package com.realifetech.sdk.content

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.domain.ScreenRepository
import com.realifetech.sdk.content.webPage.domain.WebPageRepository
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.domain.WidgetsRepository
import com.realifetech.type.ScreenType
import com.realifetech.type.WebPageType
import javax.inject.Inject

class Content @Inject constructor(
    private val webPageRepository: WebPageRepository,
    private val widgetsRepository: WidgetsRepository,
    private val screenRepository: ScreenRepository
) {

    fun getWebPage(
        webPage: WebPageType,
        callback: (error: Exception?, result: FragmentWebPage?) -> Unit
    ) {

        webPageRepository.getWebPageByType(webPage, callback)
    }

    fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    ) =
        widgetsRepository.getWidgetsByScreenId(id, pageSize, page, callback)

    fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    ) =
        widgetsRepository.getWidgetsByScreenType(screenType, pageSize, page, callback)

    fun getScreenTitleById(
        id: String,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    ) =
        screenRepository.getScreenById(id, callback)

    fun getScreenTitleByScreenType(
        screenType: ScreenType,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    ) {
        screenRepository.getScreenByScreenType(screenType, callback)
    }

}