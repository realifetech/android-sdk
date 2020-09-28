package com.realifetech.sdk.domain

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