package com.realifetech.sdk.communicate.domain

interface PushNotificationStorage {
    var token: String
    fun removeToken()
}