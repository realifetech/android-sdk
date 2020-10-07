package com.realifetech.sdk.communicate

import com.realifetech.sdk.communicate.di.CommunicateProvider
import com.realifetech.sdk.communicate.domain.TokenBody

class Communicate {

    fun registerForPushNotifications(token: String) {
        CommunicateProvider.provideApiService().pushNotifications(ID, TokenBody(GOOGLE, token)).execute()
    }

    private object Holder {
        val instance = Communicate()
    }

    companion object {
        private const val ID = "me"
        private const val GOOGLE = "GOOGLE"
        val instance: Communicate by lazy { Holder.instance }
    }
}