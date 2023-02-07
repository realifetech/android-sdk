package com.realifetech.sdk.access

import com.realifetech.sdk.access.data.model.TicketAuth
import com.realifetech.sdk.access.domain.AccessRepository
import javax.inject.Inject

class Access @Inject constructor(private val accessRepository: AccessRepository) {
    fun getMyTicketAuths(callback: (error: Exception?, tickets: List<TicketAuth?>?) -> Unit) {
        accessRepository.getMyTicketAuths(callback)
    }
}