package com.realifetech.sdk.communicate.data.repositories

import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.api.toInput
import com.apollographql.apollo3.exception.ApolloException
import com.realifetech.GetMyDeviceNotificationConsentsQuery
import com.realifetech.GetNotificationConsentsQuery
import com.realifetech.UpdateMyDeviceConsentMutation
import com.realifetech.UpdateMyDeviceNotificationConsentMutation
import com.realifetech.sdk.RealifeTech.apolloClient
import com.realifetech.sdk.communicate.data.model.toDomainModel
import com.realifetech.sdk.communicate.domain.NotificationConsentRepository
import com.realifetech.sdk.communicate.domain.model.DeviceNotificationConsent
import com.realifetech.sdk.communicate.domain.model.NotificationConsent
import com.realifetech.type.*

class NotificationConsentRepositoryImpl : NotificationConsentRepository {
    override suspend fun getNotificationConsents(): List<NotificationConsent?> {
        val activeStatus = NotificationConsentFilter(Optional.Present(ConsentStatus.ACTIVE))
        try {
            val response = apolloClient.query(GetNotificationConsentsQuery(filter = activeStatus.toInput()))
                .execute()

            if (response.hasErrors()) {
                throw ApolloException(response.errors?.first()?.message)
            }

            return response.data?.getNotificationConsents?.map {
                it?.notificationConsentFragment?.toDomainModel()
            } ?: emptyList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun getMyDeviceNotificationConsents(): List<DeviceNotificationConsent?> {
        try {
            val response = apolloClient.query(GetMyDeviceNotificationConsentsQuery()).execute()

            if (response.hasErrors()) {
                throw ApolloException(response.errors?.first()?.message)
            }

            return response.data?.getMyDeviceNotificationConsents?.map {
                DeviceNotificationConsent(
                    id = it?.id ?: "",
                    enabled = it?.enabled ?: false,
                    consent = it?.consent?.notificationConsentFragment?.toDomainModel() ?: NotificationConsent.empty()
                )
            } ?: emptyList()
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateMyDeviceNotificationConsent(id: String, enabled: Boolean): Boolean {
        val input = DeviceNotificationConsentInput(notificationConsentId = id, enabled = enabled)
        val mutation = UpdateMyDeviceNotificationConsentMutation(input = input)
        try {
            val response = apolloClient.mutate(mutation).execute()

            if (response.hasErrors()) {
                throw ApolloException(response.errors?.first()?.message)
            }

            return response.data?.updateMyDeviceNotificationConsent != null
        } catch (e: Exception) {
            throw e
        }
    }

    override suspend fun updateMyDeviceConsent(
        calendar: Boolean,
        camera: Boolean,
        locationCapture: Boolean,
        locationGranular: LocationGranular,
        photoSharing: Boolean,
        pushNotification: Boolean
    ): Boolean {
        val input = DeviceConsentInput(
            calendar = Optional.Present(calendar),
            camera = Optional.Present(camera),
            locationCapture = Optional.Present(locationCapture),
            locationGranular = Optional.Present(locationGranular),
            photoSharing = Optional.Present(photoSharing),
            pushNotification = Optional.Present(pushNotification)
        )
        val mutation = UpdateMyDeviceConsentMutation(input = input)

        try {
            val response = apolloClient.mutate(mutation).execute()

            if (response.hasErrors()) {
                throw ApolloException(response.errors?.first()?.message)
            }

            return response.data?.updateMyDeviceConsent != null
        } catch (e: Exception) {
            throw e
        }
    }

}

