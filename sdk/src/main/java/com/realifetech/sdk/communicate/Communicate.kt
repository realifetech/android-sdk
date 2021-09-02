package com.realifetech.sdk.communicate

import android.content.Context
import com.realifetech.sdk.communicate.domain.PushNotificationsTokenStorage
import com.realifetech.sdk.communicate.data.TokenBody
import com.realifetech.sdk.core.network.RealifetechApiV3Service
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.general.utils.hasNetworkConnection

class Communicate (
    private val tokenStorage: PushNotificationsTokenStorage,
    private val realifetechApiV3Service: RealifetechApiV3Service,
    private val context: Context
) {

    fun registerForPushNotifications(token: String): Result<Boolean> {
        tokenStorage.pendingToken = token

        if (!context.hasNetworkConnection) {
            return Result.Error(java.lang.RuntimeException("No Internet connection"))
        }

        val response =
            realifetechApiV3Service.pushNotifications(ID, TokenBody(GOOGLE, token))
                .execute()
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


    companion object {
        private const val ID = "me"
        private const val GOOGLE = "GOOGLE"
    }
}