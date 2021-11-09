package com.realifetech.sdk.util

import com.apollographql.apollo.api.Error


object Constants {

    const val EMPTY = ""
    const val ERROR_MESSAGE = "something went wrong"
    val errors = listOf(Error(ERROR_MESSAGE))
}