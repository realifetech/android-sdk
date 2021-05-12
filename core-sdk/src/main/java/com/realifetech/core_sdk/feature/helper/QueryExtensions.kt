package com.realifetech.core_sdk.feature.helper

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.exception.ApolloException
import com.realifetech.core_sdk.domain.Result

fun <T : Any>T?.extractResponse(errors: List<Error>?): Result<T> {
    return try {
        this?.let {
            Result.Success(this)
        } ?: run {
            val errorMessage = errors?.firstOrNull()?.message ?: "Unknown error"
            Result.Error(RuntimeException(errorMessage))
        }
    } catch (exception: ApolloException) {
        Result.Error(exception)
    }
}