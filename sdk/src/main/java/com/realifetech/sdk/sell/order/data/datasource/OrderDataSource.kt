package com.realifetech.sdk.sell.order.data.datasource

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject

interface OrderDataSource {
    fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    )

    fun getOrderById(id: String, callback: (error: Exception?, order: Order?) -> Unit)
    fun updateMyOrder(
        id: String,
        orderUpdateWrapper: OrderUpdateWrapper,
        callback: (error: Exception?, order: Order?) -> Unit
    )
}