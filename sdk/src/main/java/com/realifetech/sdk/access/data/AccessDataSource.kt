package com.realifetech.sdk.access.data

import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject

interface AccessDataSource {

    fun getMyTickets(pageSize: Int, callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit)

    fun getMyTicketById(id: Int, callback: (error: Exception?, response: Ticket?) -> Unit)

    fun getNextUpcomingTicket(callback: (error: Exception?, ticket: Ticket?) -> Unit)

    fun getMyTicketAuths()

}