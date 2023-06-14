package com.realifetech.sdk.analytics

import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.domain.AnalyticsEngine
import com.realifetech.sdk.analytics.domain.AnalyticsStorage
import com.realifetech.sdk.core.data.database.preferences.configuration.ConfigurationStorage
import com.realifetech.sdk.core.domain.LinearRetryPolicy
import com.realifetech.sdk.core.domain.RetryPolicy
import com.realifetech.sdk.core.utils.DeviceCalendar
import com.realifetech.sdk.general.General
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class Analytics(
    private val engine: AnalyticsEngine,
    private val storage: AnalyticsStorage,
    private val general: General,
    private val dispatcherIO: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher,
    private val timeUtils: DeviceCalendar,
    private val configurationStorage: ConfigurationStorage
) {

    private val retryPolicy: RetryPolicy = LinearRetryPolicy(TimeUnit.SECONDS.toMillis(45)) {
        GlobalScope.launch(dispatcherIO) {
            if (engine.sendPendingEvents()) {
                cancel()
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
    suspend fun track(
        type: String,
        action: String,
        new: Map<String, Any>?,
        old: Map<String, Any>?
    ): Boolean {
        val event = AnalyticEventWrapper(
            type,
            action,
            configurationStorage.userId,
            new,
            old,
            timeUtils.currentTime
        )
        return if (general.isSdkReady) {
            try {
                val success = engine.track(event)
                retryPolicy.cancel()
                success
            } catch (e: Exception) {
                retryPolicy.execute()
                throw e
            }
        } else {
            storage.save(event)
            throw RuntimeException(RUNTIME_EXCEPTION_MESSAGE)
        }
    }

    companion object {
        private const val RUNTIME_EXCEPTION_MESSAGE = "The SDK is not ready yet"
    }
}