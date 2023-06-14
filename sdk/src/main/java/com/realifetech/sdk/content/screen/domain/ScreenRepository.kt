package com.realifetech.sdk.content.screen.domain

import com.realifetech.sdk.content.screen.data.datasource.ScreensDataSource
import com.realifetech.sdk.content.screen.data.model.Translation
import com.realifetech.type.ScreenType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject

class ScreenRepository @Inject constructor(private val dataSource: ScreensDataSource) {
    suspend fun getScreenById(id: String): List<Translation> {
        return try {
            withContext(Dispatchers.IO) { dataSource.getScreenById(id) }
        } catch (exception: Exception) {
            Timber.e(exception, "Error getting screen by id")
            listOf()
        }
    }

    suspend fun getScreenByScreenType(screenType: ScreenType): List<Translation> {
        return try {
            withContext(Dispatchers.IO) { dataSource.getScreenByScreenType(screenType) }
        } catch (exception: Exception) {
            Timber.e(exception, "Error getting screen by screen type")
            listOf()
        }
    }
}