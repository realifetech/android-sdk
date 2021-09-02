package com.realifetech.sdk.content.widgets.domain

import com.realifetech.sdk.content.widgets.data.WidgetEdge
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType

interface WidgetsDataSource {
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