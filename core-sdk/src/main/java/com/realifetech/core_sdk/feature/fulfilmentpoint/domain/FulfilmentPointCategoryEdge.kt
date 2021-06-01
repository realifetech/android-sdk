package com.realifetech.core_sdk.feature.fulfilmentpoint.domain

data class FulfilmentPointCategoryEdge(
    val nextPage: Int?,
    val lastPage: Int?,
    val firstPage: Int?,
    val items: List<FulfilmentPointCategory>
)
