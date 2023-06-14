package com.realifetech.sdk.content.webPage.data

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.type.WebPageType

interface WebPageDataSource {
    suspend fun getWebPageByType(type: WebPageType): FragmentWebPage?
}