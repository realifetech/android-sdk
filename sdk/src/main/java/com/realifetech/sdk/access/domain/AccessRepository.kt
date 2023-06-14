package com.realifetech.sdk.access.domain

import com.realifetech.sdk.access.data.AccessDataSource
import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import timber.log.Timber
import javax.inject.Inject

class AccessRepository @Inject constructor(private val accessDataSource: AccessDataSource) {
    suspend fun getMyTickets(pageSize: Int): PaginatedObject<Ticket?> {
        return try {
            val tickets = accessDataSource.getMyTickets(pageSize)
            Timber.d("Retrieved tickets: $tickets")
            tickets
        } catch (e: Exception) {
            Timber.e(e, "Failed to get tickets")
            throw e
        }
    }

    suspend fun getMyTicketById(id: Int): Ticket? {
        return try {
            val ticket = accessDataSource.getMyTicketById(id)
            Timber.d("Retrieved ticket: $ticket")
            ticket
        } catch (e: Exception) {
            Timber.e(e, "Failed to get ticket by id: $id")
            throw e
        }
    }

    suspend fun getNextUpcomingTicket(): Ticket? {
        return try {
            val ticket = accessDataSource.getNextUpcomingTicket()
            Timber.d("Retrieved next upcoming ticket: $ticket")
            ticket
        } catch (e: Exception) {
            Timber.e(e, "Failed to get next upcoming ticket")
            throw e
        }
    }

    suspend fun getMyTicketAuths(): List<TicketAuth?> {
        return try {
            val auths = accessDataSource.getMyTicketAuths()
            Timber.d("Retrieved ticket auths: $auths")
            auths
        } catch (e: Exception) {
            Timber.e(e, "Failed to get ticket auths")
            throw e
        }
    }
}
