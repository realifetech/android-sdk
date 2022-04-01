package com.realifetech.sdk.identity.sso.data

import com.realifetech.fragment.FragmentUserAlias

interface SSODataSource {
    fun getUserAlias(callback: (error: Exception?, user: FragmentUserAlias?) -> Unit)
}