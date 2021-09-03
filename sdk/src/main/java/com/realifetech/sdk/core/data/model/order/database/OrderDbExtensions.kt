package com.realifetech.sdk.core.data.model.order.database

import com.realifetech.sdk.core.data.model.order.model.Order

val Order.asEntity: OrderEntity
    get() = OrderEntity(id, this)

val OrderEntity.asModel
    get() = order