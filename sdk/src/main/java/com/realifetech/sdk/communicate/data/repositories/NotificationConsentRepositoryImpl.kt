package com.realifetech.sdk.communicate.data.repositories

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
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
    override fun getNotificationConsents(callback: (error: Exception?, response: List<NotificationConsent?>?) -> Unit) {
        if (callback == null) {
            throw IllegalArgumentException("Callback must not be null")
        }
        val activeStatus = NotificationConsentFilter(ConsentStatus.ACTIVE.toInput())
        try {
            val response =
                apolloClient.query(GetNotificationConsentsQuery(filter = activeStatus.toInput()))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetNotificationConsentsQuery.Data>() {
                override fun onResponse(response: Response<GetNotificationConsentsQuery.Data>) {
                    try {
                        response.data?.getNotificationConsents?.let { notificationConsentList ->
                            notificationConsentList.map { getNotificationConsent ->
                                getNotificationConsent?.fragments?.notificationConsentFragment?.toDomainModel()
                            }.let { notificationConsentList ->
                                callback.invoke(
                                    null,
                                    notificationConsentList
                                )
                            }
                        }
                    } catch (e: Exception) {
                        callback.invoke(e, null)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (e: Exception) {
            callback.invoke(e, null)
        }
    }

    override fun getMyDeviceNotificationConsents(callback: (error: Exception?, response: List<DeviceNotificationConsent?>?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyDeviceNotificationConsentsQuery())
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
            response.enqueue(object :
                ApolloCall.Callback<GetMyDeviceNotificationConsentsQuery.Data>() {
                override fun onResponse(response: Response<GetMyDeviceNotificationConsentsQuery.Data>) {
                    val deviceNotificationConsentList =
                        response.data?.getMyDeviceNotificationConsents?.let { getMyDeviceNotificationConsent ->
                            getMyDeviceNotificationConsent.map {
                                DeviceNotificationConsent(
                                    id = it?.id ?: "",
                                    enabled = it?.enabled ?: false,
                                    consent = it?.consent?.fragments?.notificationConsentFragment?.toDomainModel() ?: NotificationConsent.empty()
                                )
                            }
                        }
                    callback.invoke(null, deviceNotificationConsentList)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (e: Exception) {
            callback.invoke(e, null)
        }
    }

    override fun updateMyDeviceNotificationConsent(
        id: String,
        enabled: Boolean,
        callback: (error: Exception?, success: Boolean?) -> Unit
    ) {
        val input = DeviceNotificationConsentInput(notificationConsentId = id, enabled = enabled)
        val mutation = UpdateMyDeviceNotificationConsentMutation(input = input)
        try {
            apolloClient.mutate(mutation).enqueue(object : ApolloCall.Callback<UpdateMyDeviceNotificationConsentMutation.Data>() {
                override fun onResponse(response: Response<UpdateMyDeviceNotificationConsentMutation.Data>) {
                    val data = response.data
                    val errors = response.errors

                    if (errors != null && errors.isNotEmpty()) {
                        callback.invoke(Exception(errors[0].message), null)
                    } else if (data?.updateMyDeviceNotificationConsent != null) {
                        callback.invoke(null, true)
                    } else {
                        callback.invoke(null, false)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (e: Exception) {
            callback.invoke(e, null)
        }
    }

    override fun updateMyDeviceConsent(
        calendar: Boolean,
        camera: Boolean,
        locationCapture: Boolean,
        locationGranular: LocationGranular,
        photoSharing: Boolean,
        pushNotification: Boolean,
        callback: (error: Exception?, success: Boolean?) -> Unit
    ) {
        val input = DeviceConsentInput(
            calendar = calendar.toInput(),
            camera = camera.toInput(),
            locationCapture = locationCapture.toInput(),
            locationGranular = locationGranular.toInput(),
            photoSharing = photoSharing.toInput(),
            pushNotification = pushNotification.toInput()
        )
        val mutation = UpdateMyDeviceConsentMutation(input = input)

        apolloClient.mutate(mutation).enqueue(object : ApolloCall.Callback<UpdateMyDeviceConsentMutation.Data>() {
            override fun onResponse(response: Response<UpdateMyDeviceConsentMutation.Data>) {
                val data = response.data
                val errors = response.errors

                if (errors != null && errors.isNotEmpty()) {
                    callback.invoke(Exception(errors[0].message), null)
                } else if (data?.updateMyDeviceConsent != null) {
                    callback.invoke(null, true)
                } else {
                    callback.invoke(null, false)
                }
            }

            override fun onFailure(e: ApolloException) {
                callback.invoke(e, null)
            }
        })
    }


}

