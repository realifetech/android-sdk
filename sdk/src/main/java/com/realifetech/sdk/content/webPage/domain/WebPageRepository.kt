package com.realifetech.sdk.content.webPage.domain

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.webPage.data.WebPageDataSource
import com.realifetech.type.WebPageType
import timber.log.Timber
import javax.inject.Inject

class WebPageRepository @Inject constructor(private val dataSource: WebPageDataSource) {
    suspend fun getWebPageByType(type: WebPageType): FragmentWebPage? {
        return try {
            dataSource.getWebPageByType(type)
        } catch (e: Exception) {
            Timber.e(e, "Error getting web page by type")
            null
        }
    }
}