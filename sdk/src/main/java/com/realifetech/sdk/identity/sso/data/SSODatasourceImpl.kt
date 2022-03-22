package com.realifetech.sdk.identity.sso.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetUserAliasesQuery
import com.realifetech.fragment.FragmentUserAlias
import javax.inject.Inject

class SSODatasourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    SSODataSource {

    override fun getUserAlias(callback: (error: Exception?, user: FragmentUserAlias?) -> Unit) {
        try {
            val response = apolloClient.query(GetUserAliasesQuery())
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
            response.enqueue(object :
                ApolloCall.Callback<GetUserAliasesQuery.Data>() {
                override fun onResponse(response: Response<GetUserAliasesQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.me?.user?.userAliases?.firstOrNull()?.fragments?.fragmentUserAlias
                    )
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