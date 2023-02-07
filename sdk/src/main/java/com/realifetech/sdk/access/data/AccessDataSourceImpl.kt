package com.realifetech.sdk.access.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyTicketAuthsQuery
import com.realifetech.sdk.RealifeTech.apolloClient
import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.access.data.model.asModel
import javax.inject.Inject

class AccessDataSourceImpl @Inject constructor(apolloClient: ApolloClient) : AccessDataSource {
    override fun getMyTickets() {
        TODO("Not yet implemented")
    }

    override fun getMyTicketById() {
        TODO("Not yet implemented")
    }

    override fun getNextUpcomingTicket() {
        TODO("Not yet implemented")
    }

    override fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyTicketAuthsQuery()).toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST).build()
            response.enqueue(object : ApolloCall.Callback<GetMyTicketAuthsQuery.Data>() {
                override fun onResponse(response: Response<GetMyTicketAuthsQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getMyTicketAuths?.map { it?.fragments?.fragmentTicketAuth?.asModel })
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