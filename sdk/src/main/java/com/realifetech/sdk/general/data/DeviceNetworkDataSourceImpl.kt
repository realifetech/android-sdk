package com.realifetech.sdk.general.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.toInput
import com.apollographql.apollo3.exception.ApolloException
import com.realifetech.SyncDeviceMutation
import com.realifetech.UpdateMyDeviceConsentMutation
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.type.DeviceConsentInput
import com.realifetech.type.DeviceInput

internal class DeviceNetworkDataSourceImpl(
    private val apolloClient: ApolloClient,
    private val deviceInfo: DeviceInfo,
    private val configuration: ConfigurationStorage
) : DeviceNetworkDataSource {

    private val input: DeviceInput
        get() {
            return DeviceInput(
                token = configuration.deviceId,
                type = Optional.Present(ANDROID),
                appVersion = Optional.Present(deviceInfo.appVersionName),
                osVersion = Optional.Present(deviceInfo.osVersion),
                model = Optional.Present(deviceInfo.model),
                manufacturer = Optional.Present(deviceInfo.manufacturer),
                bluetoothOn = Optional.Present(deviceInfo.isBluetoothEnabled),
                wifiConnected = Optional.Present(deviceInfo.isWifiOn),
                tokens = Optional.Present(emptyList())
            )
        }

    override suspend fun registerDevice(): com.realifetech.sdk.core.utils.Result<Boolean> {
        return try {
            val response = apolloClient.mutation(SyncDeviceMutation(input)).execute()
            if (response.hasErrors()) {
                throw ApolloException(response.errors?.firstOrNull()?.message ?: "")
            }
            com.realifetech.sdk.core.utils.Result.Success(response.data?.syncDevice?.acknowledged ?: false)
        } catch (exception: Exception) {
            com.realifetech.sdk.core.utils.Result.Error(exception)
        }
    }

    override suspend fun updateMyDeviceConsent(deviceConsent: DeviceConsent): com.realifetech.sdk.core.utils.Result<Boolean> {
        val input = DeviceConsentInput(
            locationCapture = deviceConsent.locationCapture.toInput(),
            locationGranular = getLocationGranular(deviceConsent.locationGranular.toString()).toInput(),
            camera = Optional.Present(deviceConsent.camera),
            calendar = Optional.Present(deviceConsent.calendar),
            photoSharing = Optional.Present(deviceConsent.photoSharing),
            pushNotification = Optional.Present(deviceConsent.pushNotification)
        )

        return try {
            val response = apolloClient.mutate(UpdateMyDeviceConsentMutation(input)).execute()
            if (response.hasErrors()) {
                throw ApolloException(response.errors?.firstOrNull()?.message ?: "")
            }
            com.realifetech.sdk.core.utils.Result.Success(response.data?.let { true } ?: false)
        } catch (e: Exception) {
            com.realifetech.sdk.core.utils.Result.Error(e)
        }
    }

    private companion object {
        private const val ANDROID = "ANDROID"
    }
}