package com.realifetech.sdk.campaignautomation.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetechCa.GetContentByExternalIdQuery

class CampaignAutomationDataSourceImplementation(private val apolloClient: ApolloClient) :
    CampaignAutomationDataSource {
    override fun getContentByExternalId(
        externalId: String,
        callback: (error: Exception?, response: GetContentByExternalIdQuery.GetContentByExternalId?) -> Unit
    ) {
        try {
            val response =

                apolloClient.query(GetContentByExternalIdQuery(externalId))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetContentByExternalIdQuery.Data>() {
                override fun onResponse(response: Response<GetContentByExternalIdQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getContentByExternalId
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