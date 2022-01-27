package com.realifetech.sdk.identity.sso.domain

import com.realifetech.GetMyUserSSOQuery.GetMyUserSSO
import com.realifetech.sdk.identity.sso.data.SSODataSource
import javax.inject.Inject

class SSORepository @Inject constructor(private val datasource: SSODataSource) {

    fun getMyUserSSO(callback: (error: Exception?, user: GetMyUserSSO?) -> Unit) {
        datasource.getMyUserSSO(callback = callback)
    }
}