package com.realifetech.sdk.campaignautomation.data.model


interface RLTCreatableFactory<in T> {
    fun create(dataModel: T): RLTViewCreatable?
}