package com.realifetech.sdk.content.screen.data.datasource

import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.type.ScreenType

interface ScreensDataSource {
    suspend fun getScreenByScreenType(screenType: ScreenType): List<Translation>
    suspend fun getScreenById(id: String): List<Translation>
}