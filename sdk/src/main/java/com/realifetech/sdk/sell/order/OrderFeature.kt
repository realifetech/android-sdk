package com.realifetech.sdk.sell.order

import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.order.wrapper.OrderUpdateWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject

class OrderFeature private constructor() {
    private val orderRepo = OrderProvider().provideOrderRepository()

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

    private object Holder {
        val instance = OrderFeature()
    }

    companion object {
        val INSTANCE: OrderFeature by lazy { Holder.instance }
    }
}