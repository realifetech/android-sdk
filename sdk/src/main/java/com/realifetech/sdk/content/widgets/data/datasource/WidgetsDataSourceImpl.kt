package com.realifetech.sdk.content.widgets.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetWidgetsByScreenIdQuery
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.data.model.asModel
import com.realifetech.type.ScreenType

class WidgetsDataSourceImpl(private val apolloClient: ApolloClient) :
    WidgetsDataSource {


    override fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(GetWidgetsByScreenTypeQuery(screenType, pageSize, page))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetWidgetsByScreenTypeQuery.Data>() {
                override fun onResponse(response: Response<GetWidgetsByScreenTypeQuery.Data>) {
                    response.data?.getWidgetsByScreenType?.fragments?.fragmentWidget?.let { fragment ->
                        callback.invoke(
                            null,
                            WidgetEdge(
                                fragment.edges?.filterNotNull()?.map { it.asModel() },
                                fragment.nextPage
                            )
                        )

                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })

        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetWidgetsByScreenIdQuery(id, pageSize, page))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetWidgetsByScreenIdQuery.Data>() {
                override fun onResponse(response: Response<GetWidgetsByScreenIdQuery.Data>) {
                    response.data?.getWidgetsByScreenId?.fragments?.fragmentWidget?.let { fragment ->
                        callback.invoke(
                            null,
                            WidgetEdge(
                                fragment.edges?.filterNotNull()?.map { it.asModel() },
                                fragment.nextPage
                            )
                        )
                    }
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