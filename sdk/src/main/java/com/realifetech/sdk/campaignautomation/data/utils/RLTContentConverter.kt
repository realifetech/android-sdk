package com.realifetech.sdk.campaignautomation.data.utils


import android.view.View
import com.realifetech.sdk.campaignautomation.data.model.RLTCreatableFactory
import com.realifetech.sdk.campaignautomation.data.model.RLTContentItem
import com.realifetech.type.ContentType


class RLTContentConverter {
    fun convert(
        items: List<RLTContentItem?>,
        factories: Map<ContentType, RLTCreatableFactory<*>>
    ): List<View?> {
        val list = mutableListOf<View?>()
        items.forEach {
            val viewItem = (factories[it?.contentType])?.create(it?.data)
            list.add(viewItem as View)
        }
        return list
    }
}
