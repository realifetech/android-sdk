package com.realifetech.sdk.access.data

import com.apollographql.apollo.ApolloClient
import javax.inject.Inject

class AccessDataSourceImpl @Inject constructor(apolloClient: ApolloClient) : AccessDataSource {
    override fun getMyTickets() {
        TODO("Not yet implemented")
    }

    override fun getMyTicketById() {
        TODO("Not yet implemented")
    }

    override fun getUpcomingTickets() {
        TODO("Not yet implemented")
    }

    override fun getMyTicketAuths() {
        TODO("Not yet implemented")
    }
}