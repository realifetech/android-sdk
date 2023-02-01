package com.realifetech.sdk.access.data

interface AccessDataSource {

    fun getMyTickets()

    fun getMyTicketById()

    fun getUpcomingTickets()

    fun getMyTicketAuths()

}