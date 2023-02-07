package com.realifetech.sdk.access.data.model

import com.realifetech.fragment.FragmentTicketAuth

val FragmentTicketAuth.TicketIntegration.asModel: TicketIntegration
    get() = TicketIntegration(id = id, name = name)

val FragmentTicketAuth.UserEmail.asModel: UserLinkedTicketEmail
    get() = UserLinkedTicketEmail(id = id, email = email, valid = valid)

val FragmentTicketAuth.asModel: TicketAuth
    get() = TicketAuth(
        id = id,
        userEmail = userEmail?.asModel,
        ticketIntegration = ticketIntegration?.asModel,
        accessToken = accessToken,
        refreshToken = refreshToken,
        expireAt = expireAt,
        clientEmail = clientEmail,
        clientId = clientId
    )
