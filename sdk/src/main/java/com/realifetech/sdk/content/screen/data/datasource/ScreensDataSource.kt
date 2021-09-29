package com.realifetech.sdk.content.screen.data.datasource

import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.type.ScreenType

interface ScreensDataSource {
    fun getScreenByScreenType(
        screenType: ScreenType,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    )

    fun getScreenById(
        id: String,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    )
}