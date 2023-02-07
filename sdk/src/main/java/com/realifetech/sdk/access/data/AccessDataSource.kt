package com.realifetech.sdk.access.data

import com.realifetech.sdk.access.data.model.TicketAuth

interface AccessDataSource {

    fun getMyTickets()

    fun getMyTicketById()

    fun getNextUpcomingTicket()

    fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit)

}