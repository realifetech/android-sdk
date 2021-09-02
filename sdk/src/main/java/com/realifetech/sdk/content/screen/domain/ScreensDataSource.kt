package com.realifetech.sdk.content.screen.domain

import com.realifetech.sdk.content.screen.data.Translation
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType

interface ScreensDataSource{
    suspend fun getScreenByScreenType(screenType: ScreenType): Result<List<Translation>>

    suspend fun getScreenById(id: String): Result<List<Translation>>
}