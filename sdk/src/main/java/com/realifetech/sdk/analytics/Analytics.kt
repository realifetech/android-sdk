package com.realifetech.sdk.analytics

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.analytics.data.AnalyticsEngine
import com.realifetech.sdk.analytics.data.AnalyticsStorage
import com.realifetech.sdk.analytics.domain.AnalyticsEvent
import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.RetryPolicy
import com.realifetech.sdk.core.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*
import java.util.concurrent.TimeUnit

class Analytics(
    private val engine: AnalyticsEngine,
    private val storage: AnalyticsStorage
) {

    private val retryPolicy: RetryPolicy = LinearRetryPolicy(RETRY_TIME_MILLISECONDS) {
        GlobalScope.launch(Dispatchers.IO) {
            sendPendingEvents()
        }
    }

    /**
     * Logs an event using the [AnalyticsEngine] provided and is calling the [completion] when finishes. If the [completion]
     * is having a null error value it means that the call was successful, otherwise the error will indicate the cause of
     * the issue.
     *
     * In case if there was an error, the event will be saved in the [storage], and the [retryPolicy] will be called, for
     * new attempts of sending pending events.
     */
    fun logEvent(
        type: String,
        action: String,
        new: Map<String, Any>?,
        old: Map<String, Any>?,
        completion: ((error: Exception?) -> Unit)?
    ) {

        GlobalScope.launch(Dispatchers.IO) {
            val event = AnalyticsEvent(type, action, new, old, Calendar.getInstance().timeInMillis)
            val errorResponse = if (RealifeTech.getGeneral().isSdkReady) {
                val result = engine.logEvent(event)

                val error = if (result is Result.Error) {
                    storage.save(event)
                    retryPolicy.execute()
                    result.exception
                } else {
                    retryPolicy.cancel()
                    null
                }
                error
            } else {
                storage.save(event)
                RuntimeException("The SDK is not ready yet")
            }

            withContext(Dispatchers.Main) {
                completion?.invoke(errorResponse)
            }
        }
    }

    /**
     * Extracts all the pending events from the [storage] and attempts to send them again using the [engine].
     * If the event was sent successfully, we will remove it from the pending [storage], otherwise nothing will be changed.
     */
    @Synchronized
    private suspend fun sendPendingEvents() {
        val allPendingEvents = storage.getAll()
        if (allPendingEvents.isEmpty()) {
            retryPolicy.cancel()
            return
        }

        allPendingEvents.forEach { pendingEvent ->
            val result = engine.logEvent(pendingEvent.event)
            if (result is Result.Success) {
                storage.remove(pendingEvent)
            }
        }
    }

    companion object {
        private val RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(45)
    }
}