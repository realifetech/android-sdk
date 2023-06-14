package com.realifetech.sdk.content

import com.realifetech.fragment.FragmentWebPage
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.sdk.content.screen.domain.ScreenRepository
import com.realifetech.sdk.content.webPage.domain.WebPageRepository
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.domain.WidgetsRepository
import com.realifetech.type.ScreenType
import com.realifetech.type.WebPageType
import timber.log.Timber
import javax.inject.Inject

class Content @Inject constructor(
    private val webPageRepository: WebPageRepository,
    private val widgetsRepository: WidgetsRepository,
    private val screenRepository: ScreenRepository
) {
    suspend fun getWebPage(webPage: WebPageType): FragmentWebPage? {
        return try {
            webPageRepository.getWebPageByType(webPage)
        } catch (e: Exception) {
            Timber.e(e, "Error getting web page")
            null
        }
    }

    suspend fun getWidgetsByScreenId(id: String, pageSize: Int, page: Int): Result<WidgetEdge> =
        widgetsRepository.getWidgetsByScreenId(id, pageSize, page)

    suspend fun getWidgetsByScreenType(screenType: ScreenType, pageSize: Int, page: Int): Result<WidgetEdge> =
        widgetsRepository.getWidgetsByScreenType(screenType, pageSize, page)

    suspend fun getScreenTitleById(id: String): List<Translation> {
        return try {
            screenRepository.getScreenById(id)
        } catch (exception: Exception) {
            Timber.e(exception, "Error getting screen title by id")
            listOf()
        }
    }

    suspend fun getScreenTitleByScreenType(screenType: ScreenType): List<Translation> {
        return try {
            screenRepository.getScreenByScreenType(screenType)
        } catch (exception: Exception) {
            Timber.e(exception, "Error getting screen title by screen type")
            listOf()
        }
    }
}