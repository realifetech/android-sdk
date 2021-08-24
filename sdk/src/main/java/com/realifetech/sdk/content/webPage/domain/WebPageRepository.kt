package com.realifetech.sdk.content.webPage.domain

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType

class WebPageRepository(private val dataSource: DataSource) {

    fun getWebPageByType(
        type: WebPageType,
        callback: (error: Exception?, result: FragmentWebPage?) -> Unit
    ) {
        dataSource.getWebPageByType(type, callback)
    }

    interface DataSource {
        fun getWebPageByType(
            type: WebPageType,
            callback: (error: Exception?, result: FragmentWebPage?) -> Unit
        )
    }
}