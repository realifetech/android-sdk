package com.realifetech.sdk.core.domain

interface RetryPolicy {
    /**
     * Starts the retry policy.
     */
    fun execute()

    /**
     * Cancels any existing running logic.
     */
    fun cancel()
}