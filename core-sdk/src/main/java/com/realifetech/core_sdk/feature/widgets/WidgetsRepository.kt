package com.realifetech.core_sdk.feature.widgets

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.type.ScreenType
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.rxSingle


class WidgetsRepository(private val dataSource: DataSource) {

    fun getWidgetsByScreenIdFlowable(
        id: String,
        pageSize: Int,
        page: Int
    ): Flowable<Result<List<Widget>>> {
        return rxSingle { dataSource.getWidgetsByScreenId(id, pageSize, page) }.toFlowable()
    }

    fun getWidgetsByScreenTypeFlowable(
        screenType: ScreenType,
        pageSize: Int,
        page: Int
    ): Flowable<Result<List<Widget>>> =
        rxSingle { dataSource.getWidgetsByScreenType(screenType, pageSize, page) }.toFlowable()

    suspend fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int
    ) =
        dataSource.getWidgetsByScreenType(screenType, pageSize, page)

    suspend fun getWidgetsByScreenId(id: String, pageSize: Int, page: Int) =
        dataSource.getWidgetsByScreenId(id, pageSize, page)

    interface DataSource {
        suspend fun getWidgetsByScreenId(
            id: String,
            pageSize: Int,
            page: Int
        ): Result<List<Widget>>

        suspend fun getWidgetsByScreenType(
            screenType: ScreenType,
            pageSize: Int,
            page: Int
        ): Result<List<Widget>>

    }
}