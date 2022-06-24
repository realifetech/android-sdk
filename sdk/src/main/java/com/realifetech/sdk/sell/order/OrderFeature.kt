package com.realifetech.sdk.sell.order

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.domain.OrderRepository
import javax.inject.Inject

class OrderFeature @Inject constructor(private val orderRepo: OrderRepository) {

    fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    ) {
        orderRepo.getOrders(pageSize, page, callback)
    }

    fun getOrderById(
        id: String,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        orderRepo.getOrderById(id, callback)
    }

    fun updateMyOrder(
        id: String,
        input: OrderUpdateWrapper,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        orderRepo.updateMyOrder(id, input, callback)
    }
}