package com.realifetech.sdk.identity

import com.realifetech.sdk.identity.sso.SSOFeature
import javax.inject.Inject

class Identity @Inject constructor(
    private val ssoFeature: SSOFeature,
) {

    fun getSSO(): SSOFeature {
        return ssoFeature
    }
}