package com.realifetech.sdk.general.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.SyncDeviceMutation
import com.realifetech.type.DeviceInput
import com.realifetech.type.DeviceTokenInput

internal class DeviceNetworkDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val deviceInfo: DeviceInfo,
) : DeviceNetworkDataSource {

    private val input: DeviceInput
        get() {
            return DeviceInput(
                token = deviceInfo.deviceId,
                type = ANDROID.toInput(),
                osVersion = deviceInfo.osVersion.toInput(),
                model = deviceInfo.model.toInput(),
                manufacturer = deviceInfo.manufacturer.toInput(),
                bluetoothOn = deviceInfo.isBluetoothEnabled.toInput(),
                wifiConnected = deviceInfo.isWifiOn.toInput(),
                tokens = listOf<DeviceTokenInput?>().toInput()
            )
        }

    override fun registerDevice(
        appVersion: String,
        callback: (error: Exception?, registered: Boolean?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(SyncDeviceMutation(input.copy(appVersion = appVersion.toInput())))
            response.enqueue(object : ApolloCall.Callback<SyncDeviceMutation.Data>() {
                override fun onResponse(response: Response<SyncDeviceMutation.Data>) {
                    response.data?.syncDevice?.acknowledged?.let {
                        callback.invoke(null, it)
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }
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