package com.realifetech.core_sdk.database.order

import com.realifetech.core_sdk.data.order.database.OrderEntity
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.database.shared.LocalStorage

interface OrdersLocalStorageManager : LocalStorage<OrderEntity, Order>