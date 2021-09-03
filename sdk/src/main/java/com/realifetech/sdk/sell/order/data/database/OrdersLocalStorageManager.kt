package com.realifetech.sdk.sell.order.data.database


import com.realifetech.sdk.core.data.model.order.database.OrderEntity
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.database.shared.LocalStorage

interface OrdersLocalStorageManager : LocalStorage<OrderEntity, Order>