package com.realifetech.sdk.sell.order.domain

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.data.datasource.OrderDataSource

class OrderRepository(
    private val dataSource: OrderDataSource,
) {

    fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    ) {
        dataSource.getOrders(pageSize, page, callback)
    }

    fun getOrderById(id: String, callback: (error: Exception?, order: Order?) -> Unit) {
        dataSource.getOrderById(id, callback)
    }

    fun updateMyOrder(
        id: String,
        input: OrderUpdateWrapper,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        dataSource.updateMyOrder(id, input, callback)
    }
}