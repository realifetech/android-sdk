package com.realifetech.sdk.content.screen.domain

import com.realifetech.sdk.content.screen.data.datasource.ScreensDataSource
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.type.ScreenType
import javax.inject.Inject

class ScreenRepository @Inject constructor(private val dataSource: ScreensDataSource) {

    fun getScreenById(
        id: String,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    ) {
        dataSource.getScreenById(id, callback)
    }

    fun getScreenByScreenType(
        screenType: ScreenType,
        callback: (error: Exception?, response: List<Translation>?) -> Unit
    ) {
        dataSource.getScreenByScreenType(screenType, callback)
    }
}