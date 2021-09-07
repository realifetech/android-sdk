package com.realifetech.sdk.analytics.mocks

import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent
import com.realifetech.sdk.analytics.data.model.AnalyticsEvent

object AnalayticsMocks {


    val event1 = generateEvents("OrderButton", "click")
    val analyticsEvent1 = event1.event
    val event2 = generateEvents("CloseButton", "click")
    val events = listOf(
        event1,
        event2
    )
    val emptyEvents = listOf<PendingAnalyticsEvent>()
    private fun generateEvents(type: String, action: String) = PendingAnalyticsEvent(
        AnalyticsEvent(
            type,
            action,
            null,
            null,
            System.currentTimeMillis()
        )
    )
}