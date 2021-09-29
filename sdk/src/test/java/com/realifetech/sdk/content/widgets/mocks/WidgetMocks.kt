package com.realifetech.sdk.content.widgets.mocks

import com.realifetech.fragment.FragmentWidget
import com.realifetech.sdk.content.widgets.data.model.WidgetEdge
import com.realifetech.sdk.content.widgets.data.model.asModel
import com.realifetech.type.*

object WidgetMocks {

    const val PAGE = 1
    const val PAGE_SIZE = 10
    const val NEXT_PAGE = 2
    val screenType: ScreenType = ScreenType.DISCOVER
    val edge = generateEdge("1")
    val edges = listOf(edge, generateEdge("2"))
    val widgets = edges.map { it.asModel() }
    val widgetEdge: WidgetEdge? = WidgetEdge(widgets, NEXT_PAGE)
    const val screenId = "1234"
    val fragmentWidget = FragmentWidget("", edges, NEXT_PAGE)
    private fun generateEdge(id: String) = FragmentWidget.Edge(
        "",
        id,
        FragmentWidget.Style("", StyleType.LIST, StyleSize.MEDIUM, false),
        "url",
        WidgetType.BANNER,
        FragmentWidget.Variation(
            "",
            WidgetFetchType.FEATURED,
            listOf("1", "2", "3", "5"),
            listOf(FragmentWidget.Param("", "userid", "1")),
            listOf(FragmentWidget.EngagementParam("", "click", "button1")),
            listOf(FragmentWidget.Translation("", Language.EN, "batata"))
        )
    )

}