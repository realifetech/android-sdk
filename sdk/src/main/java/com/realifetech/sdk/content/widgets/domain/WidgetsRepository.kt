package com.realifetech.sdk.content.widgets.domain

import com.realifetech.sdk.content.widgets.data.WidgetEdge
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.rxSingle
import javax.inject.Inject


class WidgetsRepository @Inject constructor(private val dataSource: WidgetsDataSource) {

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


}