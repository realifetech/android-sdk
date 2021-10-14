package com.realifetech.sdk.audiences.mocks

import com.apollographql.apollo.api.Error

object AudiencesMocks {

    const val expectedErrorMessage = "HTTP error with code = 0 & message = "
    val errors = listOf(Error("something"))
    const val externalAudienceId = "external id"
}