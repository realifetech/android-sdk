package com.realifetech.sdk.content.screen.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetScreenByIdQuery
import com.realifetech.GetScreenByScreenTypeQuery
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.data.model.mapToTranslation
import com.realifetech.type.ScreenType

class ScreenDataSourceImpl(private val apolloClient: ApolloClient) :
    ScreensDataSource {
    override fun getScreenByScreenType(
        screenType: ScreenType,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetScreenByScreenTypeQuery(screenType))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetScreenByScreenTypeQuery.Data>() {
                override fun onResponse(response: Response<GetScreenByScreenTypeQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getScreenByScreenType?.translations?.map { result -> result.mapToTranslation() })

                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })

        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getScreenById(
        id: String,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetScreenByIdQuery(id))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetScreenByIdQuery.Data>() {
                override fun onResponse(response: Response<GetScreenByIdQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getScreenById?.translations?.map { result -> result.mapToTranslation() })
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