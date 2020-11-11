package com.realifetech.sdk.communicate.data

class PushNotificationsTokenStorage(private val dataSource: DataSource) {

    var pendingToken: String
        get() = dataSource.token
        set(value) {
            dataSource.token = value
        }

    val hasPendingToken: Boolean
        get() = pendingToken.isNotEmpty()

    fun removePendingToken() = dataSource.removeToken()

    interface DataSource {
        var token: String

        fun removeToken()
    }
}