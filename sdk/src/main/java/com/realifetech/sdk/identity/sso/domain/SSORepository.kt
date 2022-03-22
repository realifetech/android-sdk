package com.realifetech.sdk.identity.sso.domain

import com.realifetech.fragment.FragmentUserAlias
import com.realifetech.sdk.identity.sso.data.SSODataSource
import javax.inject.Inject

class SSORepository @Inject constructor(private val datasource: SSODataSource) {

    fun getUserAlias(callback: (error: Exception?, user: FragmentUserAlias?) -> Unit) {
        datasource.getUserAlias(callback = callback)
    }
}