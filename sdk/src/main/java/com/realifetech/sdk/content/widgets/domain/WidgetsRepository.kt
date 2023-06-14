package com.realifetech.sdk.content.widgets.domain

import com.realifetech.sdk.content.widgets.data.datasource.WidgetsDataSource
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.type.ScreenType
import javax.inject.Inject


class WidgetsRepository @Inject constructor(private val dataSource: WidgetsDataSource) {

    suspend fun getWidgetsByScreenId(id: String, pageSize: Int, page: Int): Result<WidgetEdge> {
        return dataSource.getWidgetsByScreenId(id, pageSize, page)
    }

    suspend fun getWidgetsByScreenType(screenType: ScreenType, pageSize: Int, page: Int): Result<WidgetEdge> {
        return dataSource.getWidgetsByScreenType(screenType, pageSize, page)
    }
}
