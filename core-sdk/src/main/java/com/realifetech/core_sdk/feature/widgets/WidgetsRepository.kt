package com.realifetech.core_sdk.feature.widgets

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.widgets.domain.Widget
import com.realifetech.type.ScreenType

class WidgetsRepository(private val dataSource: DataSource) {

    suspend fun getWidgetsForScreen(screenType: ScreenType) = dataSource.getWidgetsForScreen(screenType)

    interface DataSource {
        suspend fun getWidgetsForScreen(screenType: ScreenType): Result<List<Widget>>
    }
}