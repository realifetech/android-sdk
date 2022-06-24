package com.realifetech.sdk.general.domain

import android.content.Context
import javax.inject.Inject

class SdkInitializationPrecondition @Inject constructor(private val context: Context?) {
    fun checkContextInitialized() {
        if (context == null) {
            throw RuntimeException("Application context shouldn't be null. Ensure you are configuring the SDK by passing the Context first")
        }
    }
}