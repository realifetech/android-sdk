package com.realifetech.sdk.core.database.order


import com.realifetech.sdk.core.data.order.database.OrderEntity
import com.realifetech.sdk.core.data.order.model.Order
import com.realifetech.sdk.core.database.shared.LocalStorage

interface OrdersLocalStorageManager : LocalStorage<OrderEntity, Order>