package com.realifetech.sdk.campaignautomation.data.model

import com.realifetechCa.GetContentByExternalIdQuery


interface RLTCreatableFactory<T> {
    fun create(item: GetContentByExternalIdQuery.Item): T
}