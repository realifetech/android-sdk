package com.realifetech.sdk.communicate

import com.realifetech.sdk.communicate.di.CommunicateProvider
import com.realifetech.sdk.communicate.domain.TokenBody
import com.realifetech.sdk.domain.Result
import com.realifetech.sdk.general.General
import com.realifetech.sdk.general.utils.hasNetworkConnection

class Communicate {

    private val tokenStorage = CommunicateProvider.tokenStorage

    fun registerForPushNotifications(token: String): Result<Boolean> {
        tokenStorage.pendingToken = token

        if (!General.instance.configuration.requireContext().hasNetworkConnection) {
            return Result.Error(java.lang.RuntimeException("No Internet connection"))
        }

        val response = CommunicateProvider.provideApiService().pushNotifications(ID, TokenBody(GOOGLE, token)).execute()
        return if (response.isSuccessful) {
            tokenStorage.removePendingToken()
            Result.Success(true)
        } else {
            Result.Error(RuntimeException(response.errorBody()?.string()))
        }
    }

    internal fun resendPendingToken() {
        if (tokenStorage.hasPendingToken) {
            registerForPushNotifications(tokenStorage.pendingToken)
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