package com.realifetech.core_sdk.feature.widgets.domain

import com.realifetech.GetWidgetsByScreenTypeQuery
import com.realifetech.type.StyleSize
import com.realifetech.type.StyleType
import com.realifetech.type.WidgetFetchType
import com.realifetech.type.WidgetType

/**
 * This class contains information about the structure of a widget. In it's form it will contain information about the content, but for non-static content, we will have to query the content in additional calls in order to present it.
 *
 * Example: this widget will have information about a news feed, along with how many articles and IDs to each one of the articles, but not the actual article content. To actually show the content of the widget, we will have to query the server for each one of the [contentIds]
 */
data class Widget(
    val id: String,
    val style: GetWidgetsByScreenTypeQuery.Style?,
    val viewAllUrl: String,

    /**
     * Indicates what type of widget do we have, be it a banner, button, ticket, etc.
     */
    val widgetType: WidgetType?,

    /**
     * [WidgetFetchType.FEATURED] - loads the items inside the widget with the corresponding resources
     * [WidgetFetchType.FEED] - we should fetch the corresponding resources in one list (e.g. from v3/news) and populate a widget item for each.
     * [WidgetFetchType.STATIC] - nothing needs to be fetched, as we will be displaying a native/static widget item, such as fulfilmentPointCategorySelector or selectedEvent
     */
    val fetchType: WidgetFetchType?,

    /**
     * If the widget has content which needs to be loaded, here we will have the ID which we will use to query the backend for content.
     */
    val contentIds: List<String>,

    val params: List<GetWidgetsByScreenTypeQuery.Param>,
    /**
     * Note: When sending back to APIV3, they need to be in the same format as they are now.
     */
    val engagementParam: List<GetWidgetsByScreenTypeQuery.EngagementParam>,

    /**
     * Contains translation for a title, if the widget has a title
     */
    val titleTranslations: List<GetWidgetsByScreenTypeQuery.Translation>
)

data class WidgetStyle(val type: StyleType?, val size: StyleSize?, val isPadded: Boolean)