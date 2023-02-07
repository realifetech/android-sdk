package com.realifetech.sdk.access.domain

import com.realifetech.sdk.access.data.AccessDataSource
import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import javax.inject.Inject

class AccessRepository @Inject constructor(private val accessDataSource: AccessDataSource) {
    fun getMyTickets(
        pageSize: Int,
        callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit
    ) = accessDataSource.getMyTickets(pageSize, callback)

    fun getMyTicketById(id: Int, callback: (error: Exception?, response: Ticket?) -> Unit) {
        accessDataSource.getMyTicketById(id, callback)
    }

    fun getNextUpcomingTicket() {
        accessDataSource.getNextUpcomingTicket()
    }

    fun getMyTicketAuths() {
        accessDataSource.getMyTicketAuths()
    }
}