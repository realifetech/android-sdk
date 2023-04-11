package com.realifetech.sdk.communicate.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyDeviceNotificationConsentsQuery
import com.realifetech.GetNotificationConsentsQuery
import com.realifetech.UpdateMyDeviceNotificationConsentMutation
import com.realifetech.sdk.RealifeTech.apolloClient
import com.realifetech.sdk.communicate.data.model.*
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ConsentStatus
import com.realifetech.type.DeviceNotificationConsentInput
import com.realifetech.type.NotificationConsentFilter

class PushConsentDataSourceImpl : PushConsentDataSource {
    override fun getNotificationConsents(callback: (error: Exception?, response: List<NotificationConsent?>?) -> Unit) {
        val activeStatus = NotificationConsentFilter(ConsentStatus.ACTIVE.toInput())
        try {
            val response =
                apolloClient.query(GetNotificationConsentsQuery(filter = activeStatus.toInput()))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetNotificationConsentsQuery.Data>() {
                override fun onResponse(response: Response<GetNotificationConsentsQuery.Data>) {
                    response.data?.getNotificationConsents?.let { notificationConsentList ->
                        notificationConsentList.map { getNotificationConsent ->
                            getNotificationConsent?.fragments?.notificationConsentFragment?.asModel()
                        }.let { notificationConsentList ->
                            callback.invoke(
                                null,
                                notificationConsentList
                            )
                        }
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

    override fun getMyNotificationConsents(callback: (error: Exception?, response: List<DeviceNotificationConsent?>?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyDeviceNotificationConsentsQuery())
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetMyDeviceNotificationConsentsQuery.Data>() {
                override fun onResponse(response: Response<GetMyDeviceNotificationConsentsQuery.Data>) {
                    val deviceNotificationConsentList = response.data?.getMyDeviceNotificationConsents?.let { getMyDeviceNotificationConsent ->
                        getMyDeviceNotificationConsent.map { DeviceNotificationConsent(
                            id = it?.id ?: "",
                            enabled = it?.enabled ?: false,
                            consent = it?.consent?.fragments?.notificationConsentFragment?.asModel() ?: emptyNotificationConsent()
                        ) }
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

    override fun updateMyNotificationConsent(
        id: String,
        enabled: Boolean,
        callback: (error: Exception?, success: Boolean?) -> Unit
    ) {
        val input = DeviceNotificationConsentInput(notificationConsentId = id, enabled = enabled)
        try {
            apolloClient.mutate(UpdateMyDeviceNotificationConsentMutation(input = input))
                .enqueue(object : ApolloCall.Callback<UpdateMyDeviceNotificationConsentMutation.Data>() {
                    override fun onResponse(response: Response<UpdateMyDeviceNotificationConsentMutation.Data>) {
                        response.data?.updateMyDeviceNotificationConsent?.let {
                            callback.invoke(null,true)
                        } ?: callback.invoke(null, false)
                    }

                    override fun onFailure(e: ApolloException) {
                        callback.invoke(e, null)
                    }

                })
        } catch (e: Exception) {
            callback.invoke(e, null)
        }

    }
}























