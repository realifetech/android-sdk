package com.realifetech.sdk.general.utils

import com.realifetech.core_sdk.domain.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T : Any> executeCallback(
    result: Result<T>,
    callback: (error: Exception?, result: T?) -> Unit
) {
    withContext(Dispatchers.Main) {
        when (result) {
            is Result.Success -> {
                callback.invoke(null, result.data)
            }
            is Result.Error -> {
                callback.invoke(result.exception, null)
            }
        }
    }
}