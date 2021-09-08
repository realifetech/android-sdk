package com.realifetech.sdk.analytics.domain

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.PutAnalyticEventMutation
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.data.model.asAnalyticEvent
import javax.inject.Inject

/**
 * Analytics engine which will send the events to RealifeTech backend using GraphQL
 */
internal class RltBackendAnalyticsEngine @Inject constructor(private val apolloClient: ApolloClient) :
    AnalyticsEngine {
    override suspend fun logEvent(
        event: AnalyticEventWrapper,
        callback: (error: Exception?, response: Boolean) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(PutAnalyticEventMutation(event.asAnalyticEvent()))
            response.enqueue(object : ApolloCall.Callback<PutAnalyticEventMutation.Data>() {
                override fun onResponse(response: Response<PutAnalyticEventMutation.Data>) {
                    response.data?.putAnalyticEvent?.let {
                        callback.invoke(null, it.success)
                    } ?: run {
                        callback.invoke(Exception("Unknown error"), false)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, false)
                }

            })

        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, false)
        }
    }
}