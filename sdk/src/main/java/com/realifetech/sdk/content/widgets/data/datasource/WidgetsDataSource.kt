package com.realifetech.sdk.content.widgets.data.datasource

import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.type.ScreenType

interface WidgetsDataSource {
    suspend fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int
    ): Result<WidgetEdge>

    suspend fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int
    ): Result<WidgetEdge>
}