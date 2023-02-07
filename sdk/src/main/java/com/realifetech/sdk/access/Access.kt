package com.realifetech.sdk.access

import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.domain.AccessRepository
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import javax.inject.Inject

class Access @Inject constructor(private val accessRepository: AccessRepository) {

    fun getMyTickets( pageSize: Int, callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit) {
        accessRepository.getMyTickets(pageSize, callback)
    }

    fun getMyTicketById(id: Int, callback: (error: Exception?, response: Ticket?) -> Unit) {
        accessRepository.getMyTicketById(id, callback)
    }

    fun getNextUpcomingTicket(callback: (error: Exception?, ticket: Ticket?) -> Unit) {
        accessRepository.getNextUpcomingTicket(callback)
    }
}