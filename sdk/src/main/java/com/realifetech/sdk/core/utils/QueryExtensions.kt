package com.realifetech.sdk.core.utils

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException

fun <T : Any> T?.extractResponse(errors: List<Error>?): Result<T> {
    return try {
        errors?.let {
            val errorMessage = it.firstOrNull()?.message ?: "Unknown error"
            Result.Error(RuntimeException(errorMessage))
        }?:run{
            Result.Success(this)
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

fun<T:Any,Q:Any>T?.invokeCallback(
    response: Response<Q>,
    callback: (error: Exception?, data: T?) -> Unit
) {
    when (val result = this.extractResponse(response.errors)) {
        is Result.Error -> callback.invoke(result.exception, null)
        is Result.Success -> callback.invoke(null, result.data)
    }
}