package com.realifetech.sdk.content

import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.WebPage
import com.realifetech.sdk.content.di.ContentProvider
import com.realifetech.type.WebPageType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Content private constructor() {

    fun getWebPage(webPage: WebPageType, callback: (error: Exception?, result: WebPage?) -> Unit) {
        val webPageRepo = ContentProvider().provideWebPageRepository()
        GlobalScope.launch(Dispatchers.IO) {
            val result = webPageRepo.getWebPageByType(webPage)
            withContext(Dispatchers.Main) {
                when (result) {
                    is Result.Success -> {
                        callback.invoke(null, result.data)
                    }
                    is Result.Error -> {
                        callback.invoke(result.exception, null)
                    }
                }
            }
        }
    }


    private object Holder {
        val instance = Content()
    }

    companion object {
        val instance: Content by lazy { Holder.instance }
    }
}