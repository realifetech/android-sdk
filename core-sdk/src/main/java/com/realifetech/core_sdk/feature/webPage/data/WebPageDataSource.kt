package com.realifetech.core_sdk.feature.webPage.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetWebPageByTypeQuery
import com.realifetech.core_sdk.feature.webPage.WebPageRepository
import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType

class WebPageDataSource() : WebPageRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override fun getWebPageByType(
        type: WebPageType,
        callback: (error: Exception?, result: FragmentWebPage?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetWebPageByTypeQuery(type))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetWebPageByTypeQuery.Data>() {
                override fun onResponse(response: Response<GetWebPageByTypeQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getWebPageByType?.fragments?.fragmentWebPage
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