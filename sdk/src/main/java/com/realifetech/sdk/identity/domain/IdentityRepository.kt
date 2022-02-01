package com.realifetech.sdk.identity.domain

import android.util.Log
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.core.utils.sha256
import com.realifetech.sdk.identity.data.IdentityDataSource
import com.realifetech.type.SignedUserInfoInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class IdentityRepository @Inject constructor(private val identityDataSource: IdentityDataSource) {

    fun attemptToLogin(
        email: String, firstName: String?, lastName: String?, salt: String,
        completion: (authToken: AuthToken?, error: Exception?) -> Unit
    ) {
        identityDataSource.getDeviceId { error, response ->
            error?.let { completion.invoke(null, it) }
            response?.let { deviceId ->
                val jsonObject = getJsonObjectForData(email, firstName, lastName, deviceId)
                Log.d("JSON_OBJECT", jsonObject)
                val encodedResult = "${jsonObject}.${salt}"
                Log.d("JSON_OBJECT_SALT", encodedResult)
                val signature = encodedResult.sha256()
                Log.d("JSON_OBJECT_SALT_HASH", signature)
                identityDataSource.generateNonce { error, response ->
                    error?.let {
                        completion.invoke(null, it)
                    }
                    response?.let { nonce ->
                        authenticateSignedUser(
                            firstName,
                            lastName,
                            email,
                            nonce,
                            signature,
                            completion
                        )
                    }
                }
            }
        }
    }

    internal fun authenticateSignedUser(
        firstName: String?,
        lastName: String?,
        emailAddress: String,
        nonce: String,
        signature: String,
        completion: (authToken: AuthToken?, error: Exception?) -> Unit
    ) {
        identityDataSource.authenticateUserBySignedUserInfo(
            SignedUserInfoInput(
                emailAddress,
                firstName.toInput(), lastName.toInput(),
                Input.absent(), Input.absent(), nonce, signature
            )
        ) { error, response ->
            error?.let {
                completion.invoke(null, it)
            }
            response?.let {
                completion.invoke(it, null)
            }
        }
    }

    internal fun getJsonObjectForData(
        email: String,
        firstName: String?,
        lastName: String?,
        deviceId: String
    ): String {
        return Json.encodeToString(
            sortedMapOf(
                Pair(EMAIL, email),
                Pair(FIRST_NAME, firstName),
                Pair(LAST_NAME, lastName),
                Pair(DEVICE, deviceId)
            ).toMap()
        )
    }

    companion object {
        private const val EMAIL = "email"
        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
        private const val DEVICE = "device"

    }
}