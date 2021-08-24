package com.realifetech.sdk.core.data.order.database

import com.realifetech.sdk.core.data.order.model.Order

val Order.asEntity: OrderEntity
    get() = OrderEntity(id, this)

val OrderEntity.asModel
    get() = order