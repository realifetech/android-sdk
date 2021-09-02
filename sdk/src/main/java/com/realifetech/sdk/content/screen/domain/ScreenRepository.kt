package com.realifetech.sdk.content.screen.domain

import com.realifetech.sdk.content.screen.data.Translation
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle
import javax.inject.Inject

class ScreenRepository @Inject constructor(private val dataSource: ScreensDataSource) {
    suspend fun getScreenById(id: String) = dataSource.getScreenById(id)

    fun getScreenByIdSingle(id: String): Single<Result<List<Translation>>> =
        rxSingle { dataSource.getScreenById(id) }

    suspend fun getScreenByScreenType(screenType: ScreenType) =
        dataSource.getScreenByScreenType(screenType)

    fun getScreenByScreenTypeSingle(screenType: ScreenType): Single<Result<List<Translation>>> =
        rxSingle { dataSource.getScreenByScreenType(screenType) }

}