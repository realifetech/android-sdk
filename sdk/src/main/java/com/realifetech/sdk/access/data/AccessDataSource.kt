package com.realifetech.sdk.access.data

interface AccessDataSource {

    fun getMyTickets()

    fun getMyTicketById()

    fun getNextUpcomingTicket()

    fun getMyTicketAuths()

}