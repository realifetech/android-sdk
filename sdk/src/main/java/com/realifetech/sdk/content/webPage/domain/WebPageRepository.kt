package com.realifetech.sdk.content.webPage.domain

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType
import javax.inject.Inject

class WebPageRepository @Inject constructor(private val dataSource: WebPageDataSource) {

    fun getWebPageByType(
        type: WebPageType,
        callback: (error: Exception?, result: FragmentWebPage?) -> Unit
    ) {
        dataSource.getWebPageByType(type, callback)
    }
}