package com.realifetech.sdk.analytics.domain

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.exception.ApolloException
import com.realifetech.PutAnalyticEventMutation
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.data.model.asAnalyticEvent
import javax.inject.Inject

/**
 * Analytics engine which will send the events to RealifeTech backend using GraphQL
 */
internal class RltBackendAnalyticsEngine @Inject constructor(
    private val apolloClient: ApolloClient,
    private val storage: AnalyticsStorage
) :
    AnalyticsEngine {
    override suspend fun track(event: AnalyticEventWrapper): Boolean {
        return try {
            val mutation = PutAnalyticEventMutation(event.asAnalyticEvent())
            val response = apolloClient.mutation(mutation).execute()

            if (response.hasErrors() || response.data?.putAnalyticEvent == null) {
                throw ApolloException("Errors in response")
            }

            response.data!!.putAnalyticEvent.success
        } catch (exception: ApolloException) {
            storage.save(event)
            throw exception // o devuelve false si quieres mantener el mismo patrón de comportamiento
        }
    }


    /**
     * Extracts all the pending events from the [storage] and attempts to send them again using the [engine].
     * If the event was sent successfully, we will remove it from the pending [storage], otherwise nothing will be changed.
     */

    @Synchronized
    override suspend fun sendPendingEvents(): Boolean {
        val allPendingEvents = storage.getAll()
        if (allPendingEvents.isEmpty()) {
            return true
        }

        allPendingEvents.forEach { pendingEvent ->
            try {
                track(pendingEvent.event)
                storage.remove(pendingEvent)
            } catch (error: Exception) {
                // Si ocurre un error al rastrear un evento, se detiene la ejecución
                // y se lanza el error. Si necesitas un comportamiento diferente,
                // puedes manejar el error de forma diferente.
                throw error
            }
        }

        return false
    }
}