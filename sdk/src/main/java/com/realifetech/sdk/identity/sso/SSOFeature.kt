package com.realifetech.sdk.identity.sso

import com.realifetech.GetMyUserSSOQuery.GetMyUserSSO
import com.realifetech.sdk.identity.sso.domain.SSORepository
import javax.inject.Inject

class SSOFeature @Inject constructor(private val ssoRepository: SSORepository) {


    fun getMyUserSSO(callback: (error: Exception?, user: GetMyUserSSO?) -> Unit) {
        return ssoRepository.getMyUserSSO(callback)
    }
}