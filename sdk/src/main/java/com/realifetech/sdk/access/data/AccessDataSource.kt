package com.realifetech.sdk.access.data

import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.access.data.model.TicketAuth

interface AccessDataSource {

    fun getMyTickets(pageSize: Int, callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit)

    fun getMyTicketById(id: Int, callback: (error: Exception?, response: Ticket?) -> Unit)

    fun getNextUpcomingTicket(callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit)

    fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit)

}