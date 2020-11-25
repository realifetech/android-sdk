package com.realifetech.core_sdk.feature.widgets

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.type.ScreenType

class WidgetsRepository(private val dataSource: DataSource) {

    suspend fun getWidgetsByScreenType(screenType: ScreenType) = dataSource.getWidgetsByScreenType(screenType)

    suspend fun getWidgetsByScreenId(id: String) = dataSource.getWidgetsByScreenId(id)

    interface DataSource {
        suspend fun getWidgetsByScreenType(screenType: ScreenType): Result<List<Widget>>

        suspend fun getWidgetsByScreenId(id: String): Result<List<Widget>>
    }
}