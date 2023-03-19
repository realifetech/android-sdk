package com.realifetech.sdk.general.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.SyncDeviceMutation
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.type.DeviceInput
import com.realifetech.type.DeviceTokenInput

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
                tokens = listOf<DeviceTokenInput?>().toInput()
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
                        response?.data?.syncDevice?.acknowledged
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

    private companion object {
        private const val ANDROID = "ANDROID"
    }
}