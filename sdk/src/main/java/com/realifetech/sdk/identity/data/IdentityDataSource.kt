package com.realifetech.sdk.identity.data

import com.realifetech.fragment.AuthToken
import com.realifetech.type.SignedUserInfoInput

interface IdentityDataSource {

    fun generateNonce(callback: (error: Exception?, response: String?) -> Unit)

    fun getDeviceId(callback: (error: Exception?, response: String?) -> Unit)

    fun authenticateUserBySignedUserInfo(
        userInfo: SignedUserInfoInput,
        callback: (error: Exception?, response: AuthToken?) -> Unit
    )

    fun deleteMyAccount(callback: (error: Exception?, success: Boolean?) -> Unit)
}