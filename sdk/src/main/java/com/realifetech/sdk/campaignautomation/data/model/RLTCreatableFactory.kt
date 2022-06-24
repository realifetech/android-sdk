package com.realifetech.sdk.campaignautomation.data.model

import kotlinx.parcelize.RawValue


interface RLTCreatableFactory<in T> {
    fun create(dataModel: @RawValue RLTDataModel?): RLTViewCreatable?
}