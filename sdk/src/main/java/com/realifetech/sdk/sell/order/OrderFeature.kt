package com.realifetech.sdk.sell.order

import com.apollographql.apollo3.api.Optional
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.type.OrderUpdateInput
import javax.inject.Inject

class OrderFeature @Inject constructor(private val orderRepo: OrderRepository) {

    suspend fun getOrders(
        pageSize: Int,
        page: Int
    ): PaginatedObject<Order?> {
        return orderRepo.getOrders(pageSize, page)
    }

    suspend fun getOrderById(id: String): Order {
        return orderRepo.getOrderById(id)
    }

    suspend fun updateMyOrder(
        id: String,
        input: OrderUpdateWrapper
    ): Order {
        val updateInput = mapOrderUpdateWrapperToOrderUpdateInput(input)
        return orderRepo.updateMyOrder(id, updateInput)
    }
}

fun mapOrderUpdateWrapperToOrderUpdateInput(input: OrderUpdateWrapper): OrderUpdateInput {
    return OrderUpdateInput(
        status = Optional.present(input.status),
        collectionPreferenceType = Optional.present(input.collectionPreferenceType),
        checkInTime = Optional.present(input.checkInTime),
        paymentStatus = Optional.present(input.paymentStatus)
    )
}