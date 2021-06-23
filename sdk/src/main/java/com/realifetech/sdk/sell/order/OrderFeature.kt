package com.realifetech.sdk.sell.order

import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.order.wrapper.OrderUpdateWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.CoreConfiguration
import com.realifetech.sdk.general.utils.executeCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class OrderFeature private constructor() {
    private val orderRepo = OrderProvider().provideOrderRepository()

    fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = orderRepo.getOrders(pageSize, page)
            executeCallback(result, callback)
        }
    }

    fun getOrderById(
        id: String,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = orderRepo.getOrderById(id)
            executeCallback(result, callback)
        }
    }

    fun updateMyOrder(
        id: String,
        input: OrderUpdateWrapper,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        GlobalScope.launch(Dispatchers.IO) {
            val result = orderRepo.updateMyOrder(id, input)
            executeCallback(result, callback)
        }
    }

    private object Holder {
        val instance = OrderFeature()
    }

    companion object {
        val INSTANCE: OrderFeature by lazy { Holder.instance }
    }
}