package com.realifetech.sdk.content.widgets.domain

import com.realifetech.sdk.content.widgets.data.datasource.WidgetsDataSource
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.core.utils.Result
import com.realifetech.type.ScreenType
import io.reactivex.Flowable
import kotlinx.coroutines.rx2.rxSingle
import javax.inject.Inject


class WidgetsRepository @Inject constructor(private val dataSource: WidgetsDataSource) {

    fun getWidgetsByScreenId(
        id: String,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    ) {
        dataSource.getWidgetsByScreenId(id, pageSize, page,callback)
    }

    fun getWidgetsByScreenType(
        screenType: ScreenType,
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: WidgetEdge?) -> Unit
    ) {
        dataSource.getWidgetsByScreenType(screenType, pageSize, page,callback)
    }
}