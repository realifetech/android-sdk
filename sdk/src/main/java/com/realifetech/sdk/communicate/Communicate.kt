package com.realifetech.sdk.communicate

import com.realifetech.sdk.communicate.di.CommunicateProvider
import com.realifetech.sdk.communicate.domain.TokenBody
import com.realifetech.sdk.domain.Result

class Communicate {

    fun registerForPushNotifications(token: String): Result<Boolean> {
        val response = CommunicateProvider.provideApiService().pushNotifications(ID, TokenBody(GOOGLE, token)).execute()
        return if (response.isSuccessful) {
            Result.Success(true)
        } else {
            Result.Error(RuntimeException(response.errorBody()?.string()))
        }
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