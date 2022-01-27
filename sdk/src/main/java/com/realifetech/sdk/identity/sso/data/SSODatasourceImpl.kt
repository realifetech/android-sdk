package com.realifetech.sdk.identity.sso.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyUserSSOQuery
import com.realifetech.GetMyUserSSOQuery.GetMyUserSSO
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SSODatasourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    SSODataSource {

    override fun getMyUserSSO(callback: (error: Exception?, user: GetMyUserSSO?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyUserSSOQuery())
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
            response.enqueue(object :
                ApolloCall.Callback<GetMyUserSSOQuery.Data>() {
                override fun onResponse(response: Response<GetMyUserSSOQuery.Data>) {
                    callback.invoke(null, response.data?.getMyUserSSO)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }
}