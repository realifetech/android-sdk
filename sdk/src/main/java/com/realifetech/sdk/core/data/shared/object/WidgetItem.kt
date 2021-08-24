package com.realifetech.sdk.core.data.shared.`object`

import com.realifetech.fragment.FragmentWidget

abstract class WidgetItem(
    var contentId: Int? = 0,
    var contentType: String? = null,
    var engagementParams: List<FragmentWidget.EngagementParam>? = null,
    var params: List<FragmentWidget.Param>? = null
)