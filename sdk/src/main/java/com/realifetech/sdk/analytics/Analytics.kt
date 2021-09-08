package com.realifetech.sdk.analytics

import com.realifetech.sdk.RealifeTech
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.RetryPolicy
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
            val event =
                AnalyticEventWrapper(type, action, new, old, Calendar.getInstance().timeInMillis)
            var errorResponse: Exception? = null
            if (RealifeTech.getGeneral().isSdkReady) {
                engine.logEvent(event) { error, response ->
                    error?.let {
                        storage.save(event)
                        retryPolicy.execute()
                        errorResponse = it
                    }?.run {
                        errorResponse = null
                        retryPolicy.cancel()
                    }
                }
            } else {
                storage.save(event)
                errorResponse = RuntimeException("The SDK is not ready yet")
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
            engine.logEvent(pendingEvent.event) { error, response ->
                if (error == null) {
                    storage.remove(pendingEvent)
                }
            }
        }
    }

    companion object {
        private val RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(45)
    }
}