package com.realifetech.sdk.general.data

import android.util.Log
import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.subscription.OperationServerMessage
import com.realifetech.SyncDeviceMutation
import com.realifetech.UpdateMyDeviceConsentMutation
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.type.DeviceConsentInput
import com.realifetech.type.DeviceInput
import com.realifetech.type.DeviceTokenInput
import com.realifetech.type.LocationGranular

internal class DeviceNetworkDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val deviceInfo: DeviceInfo,
    private val configuration: ConfigurationStorage
) : DeviceNetworkDataSource {

    private val input: DeviceInput
        get() {
            return DeviceInput(
                token = deviceInfo.deviceId,
                type = ANDROID.toInput(),
                appVersion = deviceInfo.appVersionName.toInput(),
                osVersion = deviceInfo.osVersion.toInput(),
                model = deviceInfo.model.toInput(),
                manufacturer = deviceInfo.manufacturer.toInput(),
                bluetoothOn = deviceInfo.isBluetoothEnabled.toInput(),
                wifiConnected = deviceInfo.isWifiOn.toInput(),
                tokens = emptyList<DeviceTokenInput?>().toInput()
            )
        }

    override fun registerDevice(callback: (error: Exception?, registered: Boolean?) -> Unit) {
        configuration.deviceId = deviceInfo.deviceId
        try {
            val response = apolloClient.mutate(SyncDeviceMutation(input))
            response.enqueue(object : ApolloCall.Callback<SyncDeviceMutation.Data>() {
                override fun onResponse(response: Response<SyncDeviceMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.syncDevice?.acknowledged ?: false
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun updateMyDeviceConsent(
        deviceConsent: DeviceConsent,
        callback: (error: Exception?, result: Boolean?) -> Unit
    ) {

        if (deviceConsent != null) {
            val input = DeviceConsentInput(
                locationCapture = deviceConsent.locationCapture.toInput(),
                locationGranular = getLocationGranular(deviceConsent.locationGranular.toString()).toInput(),
                camera = deviceConsent.camera.toInput(),
                calendar = deviceConsent.calendar.toInput(),
                photoSharing = deviceConsent.photoSharing.toInput(),
                pushNotification = deviceConsent.pushNotification.toInput()
            )

            try {
                apolloClient.mutate(UpdateMyDeviceConsentMutation(input))
                    .enqueue(object : ApolloCall.Callback<UpdateMyDeviceConsentMutation.Data>() {
                        override fun onResponse(response: Response<UpdateMyDeviceConsentMutation.Data>) {
                            response.data?.let { callback.invoke(null, true) } ?: callback.invoke( Exception("Apollo response no data exception"), null) }

                        override fun onFailure(e: ApolloException) {
                            Log.e("Update my device consent", "${e.message}")
                            callback.invoke(e, null)
                        }
                    })
            } catch (e: Exception) {
                Log.e("Update my device consent", "${e.message}")
                callback.invoke(e, null)
            }
        } else return

    }

    private companion object {
        private const val ANDROID = "ANDROID"
    }
}