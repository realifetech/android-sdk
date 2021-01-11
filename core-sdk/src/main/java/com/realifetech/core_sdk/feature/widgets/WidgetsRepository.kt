package com.realifetech.core_sdk.feature.widgets

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.domain.Translation
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.type.ScreenType
import io.reactivex.Flowable
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle


class WidgetsRepository(private val dataSource: DataSource) {

    fun getWidgetsByScreenIdFlowable(id: String): Flowable<Result<List<Widget>>> {
        return rxSingle { dataSource.getWidgetsByScreenId(id) }.toFlowable()
    }

    fun getWidgetsByScreenTypeFlowable(screenType: ScreenType): Flowable<Result<List<Widget>>> =
        rxSingle { dataSource.getWidgetsByScreenType(screenType) }.toFlowable()

    suspend fun getWidgetsByScreenType(screenType: ScreenType) =
        dataSource.getWidgetsByScreenType(screenType)

    suspend fun getWidgetsByScreenId(id: String) = dataSource.getWidgetsByScreenId(id)

    suspend fun getScreenById(id: String) = dataSource.getScreenById(id)

    fun getScreenByIdSingle(id: String): Single<Result<List<Translation>>> =
        rxSingle { dataSource.getScreenById(id) }

    suspend fun getScreenByScreenType(screenType: ScreenType) =
        dataSource.getScreenByScreenType(screenType)

    fun getScreenByScreenTypeSingle(screenType: ScreenType): Single<Result<List<Translation>>> =
        rxSingle { dataSource.getScreenByScreenType(screenType) }

    interface DataSource {
        suspend fun getWidgetsByScreenType(screenType: ScreenType): Result<List<Widget>>

        suspend fun getWidgetsByScreenId(id: String): Result<List<Widget>>

        suspend fun getScreenByScreenType(screenType: ScreenType): Result<List<Translation>>

        suspend fun getScreenById(id: String): Result<List<Translation>>
    }
}