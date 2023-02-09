package com.realifetech.sdk.identity.mocks

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.toInput
import com.realifetech.fragment.AuthToken
import com.realifetech.type.SignedUserInfoInput

object IdentityMocks {
    const val DICTIONARY ="{\"device\":\"12340\",\"email\":\"test@gmail.com\",\"firstName\":\"Fabio\",\"lastName\":\"BigOnion\"}"
    const val NONCE = "yjtmwiotkjoxahiiztggbglfmdtxtlct"
    const val SALT = "xcsfbtmddsjnawceuvlxjpxxzdlpjaxm"
    const val HASHED_VALUE = "4b161418e908e699d76cba6988ebeb73594ff5bfac0804b8c1e21eaae72ef9f6"
    const val deviceId="12340"
    const val authUrl= "www.google.com"
    const val provider="google"
    val userInfo = SignedUserInfoInput(
        "test@gmail.com","Fabio".toInput(), "BigOnion".toInput(), Input.absent(),
        Input.absent(), NONCE, HASHED_VALUE
    )
    val authToken = AuthToken(
        "AuthToken",
        "iMmU5NzM0Y2NiOTI5ZmQ2ZTcxYzMwMDUyY2IyYmRlNzJhNTBhYzgxZg",
        "Bearer",
        100,
        null,
        null
    )

}