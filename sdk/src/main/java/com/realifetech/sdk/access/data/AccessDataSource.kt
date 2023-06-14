package com.realifetech.sdk.access.data

import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject

interface AccessDataSource {

    suspend fun getMyTickets(
        pageSize: Int
    ): PaginatedObject<Ticket?>

    suspend fun getMyTicketById(
        id: Int
    ): Ticket?

    suspend fun getNextUpcomingTicket(): Ticket?

    suspend fun getMyTicketAuths(): List<TicketAuth?>

}