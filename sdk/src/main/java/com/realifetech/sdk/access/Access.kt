package com.realifetech.sdk.access

import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.access.domain.AccessRepository
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Access @Inject constructor(
    private val accessRepository: AccessRepository,
    private val dispatcherIO: CoroutineDispatcher
) {

    suspend fun getMyTickets(pageSize: Int): PaginatedObject<Ticket?> = withContext(dispatcherIO) {
        accessRepository.getMyTickets(pageSize)
    }

    suspend fun getMyTicketById(id: Int): Ticket? = withContext(dispatcherIO) {
        accessRepository.getMyTicketById(id)
    }

    suspend fun getNextUpcomingTicket(): Ticket? = withContext(dispatcherIO) {
        accessRepository.getNextUpcomingTicket()
    }

    suspend fun getMyTicketAuths(): List<TicketAuth?> = withContext(dispatcherIO) {
        accessRepository.getMyTicketAuths()
    }
}