package com.realifetech.sdk.core.domain

import java.util.*
import kotlin.concurrent.fixedRateTimer

class LinearRetryPolicy(
    private val timeIntervalMilliseconds: Long,
    private val action: TimerTask.() -> Unit
) : RetryPolicy {

    private var currentRunningTimer: Timer? = null

    override fun execute() {
        if (currentRunningTimer != null) return

        currentRunningTimer = fixedRateTimer(
            startAt = Calendar.getInstance().time,
            period = timeIntervalMilliseconds,
            action = this.action
        )
    }

    override fun cancel() {
        currentRunningTimer?.cancel()
    }
}