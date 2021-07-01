package com.realifetech.sdk.analytics.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.google.gson.Gson
import com.realifetech.PutAnalyticEventMutation
import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.domain.Result
import com.realifetech.type.AnalyticEvent
import java.text.SimpleDateFormat
import java.util.*

/**
 * Analytics engine which will send the events to RealifeTech backend using GraphQL
 */
internal class RltBackendAnalyticsEngine(private val apolloClient: ApolloClient) : AnalyticsEngine {
    override suspend fun logEvent(event: AnalyticsEvent): Result<Boolean> {
        val newInfoConverted = event.new?.let { Gson().toJson(it) }
        val oldInfoConverted = event.old?.let { Gson().toJson(it) }

        val backendEvent = AnalyticEvent(
            event.type,
            event.action,
            Input.optional(newInfoConverted),
            Input.optional(oldInfoConverted),
            "1.0",
            SimpleDateFormat("yyyy-MM-d'T'HH:mm:ssXXX").format(Date(event.creationTimeMillisecondsSince1970))
        )


        return try {
            val response = apolloClient.mutate(PutAnalyticEventMutation(backendEvent)).await()
            val data = response.data
            when {
                data?.putAnalyticEvent?.success == true -> Result.Success(true)
                data != null -> {
                    // We mark it as success because the call was successful but the backend didn't accept the pyaload.
                    Result.Success(false)
                }
                else -> {
                    Result.Error(Exception(response.errors?.firstOrNull()?.message ?: "Unknown error"))
                }
            }
        } catch (e: Exception) {
            Result.Error(e)
        }
    }
}