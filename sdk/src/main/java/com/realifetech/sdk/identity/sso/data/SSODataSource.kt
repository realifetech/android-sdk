package com.realifetech.sdk.identity.sso.data

import com.realifetech.GetMyUserSSOQuery.GetMyUserSSO

interface SSODataSource {
    fun getMyUserSSO(callback: (error: Exception?, user: GetMyUserSSO?) -> Unit)
}