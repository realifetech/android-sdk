package com.realifetech.sdk.analytics.mocks

import com.realifetech.PutAnalyticEventMutation
import com.realifetech.sdk.analytics.data.database.Entity.PendingAnalyticsEvent
import com.realifetech.sdk.analytics.data.model.AnalyticEventWrapper
import com.realifetech.sdk.analytics.data.model.asAnalyticEvent

object AnalyticsMocks {


    val event1 = generateEvents("OrderButton", "click")
    val event2 = generateEvents("CloseButton", "click")
    val events = listOf(
        event1,
        event2
    )
    val emptyEvents = listOf<PendingAnalyticsEvent>()
    val analyticEventWrapper = event1.event
    val analyticEvent = analyticEventWrapper.asAnalyticEvent()
    val successData =
        PutAnalyticEventMutation.Data(
            PutAnalyticEventMutation.PutAnalyticEvent(
                "",
                true,
                "event saved successfully"
            )
        )
    private fun generateEvents(type: String, action: String) = PendingAnalyticsEvent(
        AnalyticEventWrapper(
            type,
            action,
            null,
            null,
            System.currentTimeMillis()
        )
    )

}