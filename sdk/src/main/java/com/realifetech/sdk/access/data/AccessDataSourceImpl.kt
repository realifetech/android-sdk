package com.realifetech.sdk.access.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyTicketsQuery
import com.realifetech.sdk.RealifeTech.apolloClient
import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import javax.inject.Inject

class AccessDataSourceImpl @Inject constructor(apolloClient: ApolloClient) : AccessDataSource {

    override fun getMyTickets(
        pageSize: Int,
        callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyTicketsQuery(pageSize))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()

            response.enqueue(object : ApolloCall.Callback<GetMyTicketsQuery.Data>(){
                override fun onResponse(response: Response<GetMyTicketsQuery.Data>) {
                    val tickets = response.data?.getMyTickets?.edges?.map { result -> result?.fragments?.fragmentTicket?.asModel }
                    val nextPage = response.data?.getMyTickets?.nextPage
                    val paginatedObject = PaginatedObject(tickets, nextPage)
                    callback.invoke(null, paginatedObject)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getMyTicketById() {
        TODO("Not yet implemented")
    }

    override fun getNextUpcomingTicket() {
        TODO("Not yet implemented")
    }

    override fun getMyTicketAuths() {
        TODO("Not yet implemented")
    }
}