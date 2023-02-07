package com.realifetech.sdk.access.domain

import com.realifetech.sdk.access.data.AccessDataSource
import com.realifetech.sdk.access.data.model.TicketAuth
import javax.inject.Inject

class AccessRepository @Inject constructor(private val accessDataSource: AccessDataSource) {
    fun getMyTickets() {
        accessDataSource.getMyTickets()
    }

    fun getMyTicketById() {
        accessDataSource.getMyTicketById()
    }

    fun getNextUpcomingTicket() {
        accessDataSource.getNextUpcomingTicket()
    }

    fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit) {
        accessDataSource.getMyTicketAuths(callback)
    }
}