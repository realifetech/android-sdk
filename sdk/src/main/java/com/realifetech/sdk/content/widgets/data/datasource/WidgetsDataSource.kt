package com.realifetech.sdk.content.widgets.data.datasource

import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.type.ScreenType

interface WidgetsDataSource {
    fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    )

    fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    )

}