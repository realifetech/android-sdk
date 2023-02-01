package com.realifetech.sdk.access.domain

import com.realifetech.sdk.access.data.AccessDataSource
import javax.inject.Inject

class AccessRepository @Inject constructor(private val accessDataSource: AccessDataSource) {
    fun getMyTickets() {
        accessDataSource.getMyTickets()
    }

    fun getMyTicketById() {
        accessDataSource.getMyTicketById()
    }

    fun getUpcomingTicket() {
        accessDataSource.getUpcomingTicket()
    }

    fun getMyTicketAuths() {
        accessDataSource.getMyTicketAuths()
    }
}