package com.realifetech.sdk.campaignautomation.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.sdk.campaignautomation.data.model.ContentResponse
import com.realifetech.type.ScreenType

class CampaignAutomationDataSourceImplementation(private val apolloClient: ApolloClient) :
    CampaignAutomationDataSource {
    override fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: ContentResponse?) -> Unit
    ) {
        try {
            val response =

//                apolloClient.query(GetContentByExternalId(externalId))
                apolloClient.query(GetWidgetsByScreenTypeQuery(ScreenType.BOOKING, 1, 1))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    .build()
            //TODO add the real query so we can pull real data
            response.enqueue(object : ApolloCall.Callback<GetWidgetsByScreenTypeQuery.Data>() {
                override fun onResponse(response: Response<GetWidgetsByScreenTypeQuery.Data>) {
                    response.data?.getWidgetsByScreenType?.fragments?.fragmentWidget?.let { fragment ->
                        callback.invoke(
                            null,
                            ContentResponse(
                                1,
                                emptyList()
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