package com.realifetech.sdk.analytics.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.coroutines.toDeferred
import com.google.gson.Gson
import com.realifetech.PutAnalyticEventMutation
import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.domain.Result
import com.realifetech.type.AnalyticEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Analytics engine which will send the events to RealifeTech backend using GraphQL
 */
internal class RtlBackendAnalyticsEngine(private val apolloClient: ApolloClient) : AnalyticsEngine {
    override suspend fun logEvent(event: AnalyticsEvent): Result<Boolean> {
        val newInfoConverted = event.new?.let { Gson().toJson(it) }
        val oldInfoConverted = event.old?.let { Gson().toJson(it) }

        val backendEvent = AnalyticEvent(
            event.type,
            event.action,
            Input.optional(newInfoConverted),
            Input.optional(oldInfoConverted),
            "1.0"
        )


        return try {
            val response = apolloClient.mutate(PutAnalyticEventMutation(backendEvent)).await()
            if (response.data?.putAnalyticEvent?.success == true) {
                Result.Success(true)
            } else {
                Result.Error(Exception())
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}