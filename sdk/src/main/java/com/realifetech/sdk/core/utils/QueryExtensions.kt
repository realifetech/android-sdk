package com.realifetech.sdk.core.utils

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.exception.ApolloException
import com.realifetech.sdk.core.domain.Result

fun <T : Any> T?.extractResponse(errors: List<Error>?): Result<T> {
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

fun <T : Any, Model : Any> T?.extractResponse(
    errors: List<Error>?,
    onSuccess: (T) -> Model
): Result<Model> {
    return try {
        this?.let {
            Result.Success(onSuccess.invoke(this))
        } ?: run {
            val errorMessage = errors?.firstOrNull()?.message ?: "Unknown error"
            Result.Error(RuntimeException(errorMessage))
        }
    } catch (exception: ApolloException) {
        Result.Error(exception)
    }
}