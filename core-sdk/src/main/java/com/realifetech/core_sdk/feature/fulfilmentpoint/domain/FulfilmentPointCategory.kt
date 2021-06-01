package com.realifetech.core_sdk.feature.fulfilmentpoint.domain

import com.realifetech.fragment.FragmentFulfilmentPointCategory

data class FulfilmentPointCategory(
    val id: String,
    val reference: String?,
    val status: String?,
    val iconImageUrl: String?,
    val position: Int?,
    val translations: List<FragmentFulfilmentPointCategory.Translation?>?
)