package com.realifetech.sdk.communicate.domain

import javax.inject.Inject

class PushNotificationsTokenStorage @Inject constructor(private val dataSource: PushNotificationStorage) {

    var pendingToken: String
        get() = dataSource.token
        set(value) {
            dataSource.token = value
        }

    val hasPendingToken: Boolean
        get() = pendingToken.isNotEmpty()

    fun removePendingToken() = dataSource.removeToken()
}