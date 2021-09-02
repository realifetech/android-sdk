package com.realifetech.sdk.content.webPage.domain

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType

interface WebPageDataSource {
    fun getWebPageByType(
        type: WebPageType,
        callback: (error: Exception?, result: FragmentWebPage?) -> Unit
    )
}