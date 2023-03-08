package com.realifetech.sdk.access

import com.realifetech.sdk.access.data.model.Ticket
import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.access.domain.AccessRepository
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class Access @Inject constructor(
    private val accessRepository: AccessRepository,
    private val dispatcherIO: CoroutineDispatcher,
    private val dispatcherMain: CoroutineDispatcher
) {

    fun getMyTickets(
        pageSize: Int,
        callback: (error: Exception?, response: PaginatedObject<Ticket?>?) -> Unit
    ) {
        accessRepository.getMyTickets(pageSize) { error, response ->
            GlobalScope.launch(dispatcherIO) {
                withContext(dispatcherMain) {
                    callback(error, response)
                }
            }
        }
    }

    fun getMyTicketById(id: Int, callback: (error: Exception?, response: Ticket?) -> Unit) {
        accessRepository.getMyTicketById(id) { error, response ->
            GlobalScope.launch(dispatcherIO) {
                withContext(dispatcherMain) {
                    callback(error, response)
                }
            }
        }
    }

    fun getNextUpcomingTicket(callback: (error: Exception?, ticketList: List<Ticket?>) -> Unit) {
        accessRepository.getNextUpcomingTicket(callback)
    }

    fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit) {
        accessRepository.getMyTicketAuths(callback)
    }
}