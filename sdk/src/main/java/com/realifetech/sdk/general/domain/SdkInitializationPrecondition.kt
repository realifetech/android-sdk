package com.realifetech.sdk.general.domain

import com.realifetech.sdk.general.General

object SdkInitializationPrecondition {
    fun checkContextInitialized() {
        if (General.instance.configuration.context == null) {
            throw RuntimeException("Application context shouldn't be null. Ensure you are configuring the SDK by passing the Context first")
        }
    }
}