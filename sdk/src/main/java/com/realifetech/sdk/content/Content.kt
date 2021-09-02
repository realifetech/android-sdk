package com.realifetech.sdk.content

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.screen.domain.ScreenRepository
import com.realifetech.sdk.content.webPage.domain.WebPageRepository
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

    fun getWidgetsByScreenId(id: String, pageSize: Int, page: Int) =
        widgetsRepository.getWidgetsByScreenIdFlowable(id, pageSize, page)

    fun getWidgetsByScreenType(screenType: ScreenType, pageSize: Int, page: Int) =
        widgetsRepository.getWidgetsByScreenTypeFlowable(screenType, pageSize, page)

    fun getScreenTitleById(id: String) =
        screenRepository.getScreenByIdSingle(id)

    fun getScreenTitleByScreenType(screenType: ScreenType) =
        screenRepository.getScreenByScreenTypeSingle(screenType)
}