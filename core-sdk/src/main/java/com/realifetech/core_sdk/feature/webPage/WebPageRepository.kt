package com.realifetech.core_sdk.feature.webPage

import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.WebPage
import com.realifetech.type.WebPageType
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.rxSingle

class WebPageRepository(private val dataSource: DataSource) {

    fun getWebPageByTypeFlowable(type: WebPageType): Flowable<Result<WebPage>> {
        return rxSingle { dataSource.getWebPageByType(type) }.toFlowable()
    }

    suspend fun getWebPageByType(type: WebPageType) = dataSource.getWebPageByType(type)

    interface DataSource {
        suspend fun getWebPageByType(type: WebPageType): Result<WebPage>
    }
}