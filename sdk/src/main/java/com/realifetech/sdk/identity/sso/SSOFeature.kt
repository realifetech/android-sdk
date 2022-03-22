package com.realifetech.sdk.identity.sso

import com.realifetech.fragment.FragmentUserAlias
import com.realifetech.sdk.identity.sso.domain.SSORepository
import javax.inject.Inject

class SSOFeature @Inject constructor(private val ssoRepository: SSORepository) {


    fun getUserAlias(callback: (error: Exception?, user: FragmentUserAlias?) -> Unit) {
        return ssoRepository.getUserAlias(callback)
    }
}