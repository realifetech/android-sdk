package com.realifetech.core_sdk.feature.widgets

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.type.ScreenType
import io.reactivex.Flowable
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

    interface DataSource {
        suspend fun getWidgetsByScreenType(screenType: ScreenType): Result<List<Widget>>

        suspend fun getWidgetsByScreenId(id: String): Result<List<Widget>>
    }
}