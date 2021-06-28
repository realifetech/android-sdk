package com.realifetech.sdk.content

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.di.ContentProvider
import com.realifetech.type.WebPageType

class Content private constructor() {

    fun getWebPage(
        webPage: WebPageType,
        callback: (error: Exception?, result: FragmentWebPage?) -> Unit
    ) {
        val webPageRepo = ContentProvider().provideWebPageRepository()
        webPageRepo.getWebPageByType(webPage, callback)
    }


    private object Holder {
        val instance = Content()
    }

    companion object {
        val instance: Content by lazy { Holder.instance }
    }
}