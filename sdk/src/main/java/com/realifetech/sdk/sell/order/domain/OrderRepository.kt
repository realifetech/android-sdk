package com.realifetech.sdk.sell.order.domain

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.data.datasource.OrderDataSource
import com.realifetech.type.OrderUpdateInput

class OrderRepository(
    private val dataSource: OrderDataSource
) {

    suspend fun getOrders(
        pageSize: Int,
        page: Int
    ): PaginatedObject<Order?> {
        return dataSource.getOrders(pageSize, page)
    }

    suspend fun getOrderById(id: String): Order {
        return dataSource.getOrderById(id)
    }

    suspend fun updateMyOrder(
        id: String,
        orderUpdateInput: OrderUpdateInput
    ): Order {
        return dataSource.updateMyOrder(id, orderUpdateInput)
    }
}