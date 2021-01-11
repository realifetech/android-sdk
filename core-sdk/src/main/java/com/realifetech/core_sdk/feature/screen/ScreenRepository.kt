package com.realifetech.core_sdk.feature.screen

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.screen.domain.Translation
import com.realifetech.type.ScreenType
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle

class ScreenRepository(private val dataSource: DataSource) {
    suspend fun getScreenById(id: String) = dataSource.getScreenById(id)

    fun getScreenByIdSingle(id: String): Single<Result<List<Translation>>> =
        rxSingle { dataSource.getScreenById(id) }

    suspend fun getScreenByScreenType(screenType: ScreenType) =
        dataSource.getScreenByScreenType(screenType)

    fun getScreenByScreenTypeSingle(screenType: ScreenType): Single<Result<List<Translation>>> =
        rxSingle { dataSource.getScreenByScreenType(screenType) }


    interface DataSource {
        suspend fun getScreenByScreenType(screenType: ScreenType): Result<List<Translation>>

        suspend fun getScreenById(id: String): Result<List<Translation>>
    }
}