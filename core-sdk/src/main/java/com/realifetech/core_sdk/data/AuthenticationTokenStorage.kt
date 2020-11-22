package com.realifetech.core_sdk.data

import java.util.*

class AuthenticationTokenStorage(private val dataSource: DataSource) {

    var expireAtMilliseconds: Long
        get() = dataSource.expireTimeSince1970Milliseconds
        set(value) {
            dataSource.expireTimeSince1970Milliseconds = value
        }

    var accessToken: String
        get() = dataSource.accessToken
        set(value) {
            dataSource.accessToken = value
        }

    val isTokenExpired: Boolean
        get() {
            val timeNow = Calendar.getInstance().timeInMillis
            return timeNow >= dataSource.expireTimeSince1970Milliseconds
        }

    interface DataSource {
        var accessToken: String
        var expireTimeSince1970Milliseconds: Long
    }
}