package com.realifetech.sdk.identity.data

import com.realifetech.fragment.AuthToken
import com.realifetech.type.SignedUserInfoInput

interface IdentityDataSource {
    suspend fun generateNonce(): String
    suspend fun getDeviceId(): String
    suspend fun authenticateUserBySignedUserInfo(userInfo: SignedUserInfoInput): AuthToken
    suspend fun deleteMyAccount(): Boolean
    suspend fun getSSO(provider: String): String?
}