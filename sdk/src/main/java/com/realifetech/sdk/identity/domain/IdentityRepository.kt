package com.realifetech.sdk.identity.domain

import android.util.Log
import com.apollographql.apollo3.api.Optional
import com.realifetech.fragment.AuthToken
import com.realifetech.sdk.core.utils.Result
import com.realifetech.sdk.core.utils.sha256
import com.realifetech.sdk.identity.data.IdentityDataSource
import com.realifetech.type.SignedUserInfoInput
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import javax.inject.Inject

class IdentityRepository @Inject constructor(private val identityDataSource: IdentityDataSource) {

    suspend fun attemptToLogin(
        email: String, firstName: String?, lastName: String?, salt: String
    ): Result<AuthToken> {
        return try {
            val deviceId = identityDataSource.getDeviceId()
            val jsonObject = getJsonObjectForData(email, firstName, lastName, deviceId)
            Log.d("JSON_OBJECT", jsonObject)
            val encodedResult = "${jsonObject}.${salt}"
            Log.d("JSON_OBJECT_SALT", encodedResult)
            val signature = encodedResult.sha256()
            Log.d("JSON_OBJECT_SALT_HASH", signature)
            val nonce = identityDataSource.generateNonce()
            authenticateSignedUser(firstName, lastName, email, nonce, signature)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private suspend fun authenticateSignedUser(
        firstName: String?,
        lastName: String?,
        emailAddress: String,
        nonce: String,
        signature: String
    ): Result<AuthToken> {
        return try {
            val response = identityDataSource.authenticateUserBySignedUserInfo(
                SignedUserInfoInput(
                    email = emailAddress,
                    firstName = Optional.Present(firstName),
                    lastName = Optional.Present(lastName),
                    nonce = nonce,
                    signature = signature
                )
            )
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    suspend fun deleteMyAccount(): Result<Boolean> {
        return try {
            Result.Success(identityDataSource.deleteMyAccount())
        } catch (e: Exception) {
            Result.Error(e)
        }
    }

    private fun getJsonObjectForData(
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

    suspend fun getSSO(provider: String): String? {
        return try {
            val result = identityDataSource.getSSO(provider)
            result ?: "No SSO URL available"
        } catch (e: Exception) {
            e.message ?: "Error retrieving SSO URL"
        }
    }

    companion object {
        private const val EMAIL = "email"
        private const val FIRST_NAME = "firstName"
        private const val LAST_NAME = "lastName"
        private const val DEVICE = "device"
    }
}