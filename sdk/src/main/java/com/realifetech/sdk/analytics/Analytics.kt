package com.realifetech.sdk.analytics

import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.RetryPolicy
import com.realifetech.sdk.core.utils.DeviceCalendar
import com.realifetech.sdk.general.General
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.concurrent.TimeUnit

class Analytics(
    private val engine: AnalyticsEngine,
    private val storage: AnalyticsStorage,
    private val general: General,
    private val dispatcherIO: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
    private val timeUtils: DeviceCalendar
) {

    internal val retryPolicy: RetryPolicy = LinearRetryPolicy(RETRY_TIME_MILLISECONDS) {
        GlobalScope.launch(dispatcherIO) {
            engine.sendPendingEvents {
                if (it) {
                    cancel()
                }
            }
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
        completion: ((
            error: Exception?
        ) -> Unit)?
    ) {
        GlobalScope.launch(dispatcherIO) {
            val event =
                AnalyticEventWrapper(type, action, new, old, timeUtils.currentTime)
            if (general.isSdkReady) {
                engine.logEvent(event) { error, response ->
                    var errorResponse: Exception? = null
                    error?.let {
                        retryPolicy.execute()
                        errorResponse = it
                    } ?: run {
                        errorResponse = null
                        retryPolicy.cancel()
                    }
                    GlobalScope.launch(dispatcherIO) {
                        withContext(dispatcherMain) {
                            completion?.invoke(errorResponse)
                        }
                    }
                }
            } else {
                storage.save(event)
                withContext(dispatcherMain) {
                    completion?.invoke(RuntimeException(RUNTIME_EXCEPTION_MESSAGE))
                }
            }

        }
    }

    companion object {
        private const val RUNTIME_EXCEPTION_MESSAGE = "The SDK is not ready yet"
        private val RETRY_TIME_MILLISECONDS = TimeUnit.SECONDS.toMillis(45)
    }
}