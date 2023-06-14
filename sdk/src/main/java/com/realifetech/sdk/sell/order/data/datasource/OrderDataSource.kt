package com.realifetech.sdk.sell.order.data.datasource

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.OrderUpdateInput

interface OrderDataSource {
    suspend fun getOrders(
        pageSize: Int,
        page: Int
    ): PaginatedObject<Order?>
    suspend fun getOrderById(id: String): Order
    suspend fun updateMyOrder(
        id: String,
        orderUpdateInput: OrderUpdateInput
    ): Order
}