package com.realifetech.core_sdk.data.order.database

import com.realifetech.core_sdk.data.order.model.Order

val Order.asEntity: OrderEntity
    get() = OrderEntity(id, this)

val OrderEntity.asModel
    get() = order