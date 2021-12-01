package com.realifetech.sdk.core.network

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
            override fun onResponse(response: ApolloInterceptor.InterceptorResponse) {
                callBack.onResponse(response)
            }

            override fun onFetch(sourceType: ApolloInterceptor.FetchSourceType?) {
                callBack.onFetch(sourceType)
            }

            override fun onFailure(e: ApolloException) {
                callBack.onFailure(e)
            }

            override fun onCompleted() {
                callBack.onCompleted()
            }

        })
    }

    override fun dispose() {
        disposed = true
    }
}