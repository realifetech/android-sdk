package com.realifetech.sdk.core.network

import com.apollographql.apollo.api.Error
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.interceptor.ApolloInterceptor
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import java.util.concurrent.Executor

class OAuth2ApolloInterceptor : ApolloInterceptor {
    @Volatile
    private var disposed = false

    override fun interceptAsync(
        request: ApolloInterceptor.InterceptorRequest,
        chain: ApolloInterceptorChain,
        dispatcher: Executor,
        callBack: ApolloInterceptor.CallBack
    ) {
        chain.proceedAsync(request, dispatcher, object : ApolloInterceptor.CallBack {
            @Suppress("UNCHECKED_CAST")
            override fun onResponse(response: ApolloInterceptor.InterceptorResponse) {
                response.parsedResponse.get().errors?.let {
                    val error = it.firstOrNull() as? Error
                    val message = error?.message ?: DEFAULT_MESSAGE
                    val errorCode = (error?.customAttributes?.getValue(EXTENSIONS)
                            as? MutableMap<String, String>)?.getValue(CODE)
                    callBack.onFailure(ApolloException(message, Throwable(errorCode)))
                } ?: run {
                    callBack.onResponse(response)
                }
            }

            override fun onFetch(sourceType: ApolloInterceptor.FetchSourceType?) {
                callBack.onFetch(sourceType)
            }

            override fun onFailure(e: ApolloException) {
                callBack.onFailure(e)
            }

            override fun onCompleted() {
            }

        })
    }

    override fun dispose() {
        disposed = true
    }

    companion object {
        const val DEFAULT_MESSAGE = "Unknown error"
        const val EXTENSIONS = "extensions"
        const val CODE = "code"
    }
}