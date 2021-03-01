package com.realifetech.core_sdk.feature.widgets

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.domain.WidgetEdge
import com.realifetech.type.ScreenType
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.rxSingle


class WidgetsRepository(private val dataSource: DataSource) {

    fun getWidgetsByScreenIdFlowable(
        id: String,
        pageSize: Int,
        page: Int
    ): Flowable<Result<WidgetEdge>> {
        return rxSingle { dataSource.getWidgetsByScreenId(id, pageSize, page) }.toFlowable()
    }

    fun getWidgetsByScreenTypeFlowable(
        screenType: ScreenType,
        pageSize: Int,
        page: Int
    ): Flowable<Result<WidgetEdge>> =
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
        ): Result<WidgetEdge>

        suspend fun getWidgetsByScreenType(
            screenType: ScreenType,
            pageSize: Int,
            page: Int
        ): Result<WidgetEdge>

    }
}