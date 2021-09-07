package com.realifetech.sdk.sell.order.mocks

import com.realifetech.GetMyOrdersQuery
import com.realifetech.fragment.FragmentOrder
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.type.CollectionPreferenceType
import com.realifetech.type.OrderStatus
import io.mockk.mockk

object OrdersMocks {

    val order1 = generateOrder("1")
    private val order2 = generateOrder("2")
    val orderUpdateWrapper = OrderUpdateWrapper(null, CollectionPreferenceType.ASAP, null)
    val ordersResponse = GetMyOrdersQuery.GetMyOrders(
        "", listOf(
            generateOrderEdge(order1), generateOrderEdge(order2)
        ), 3
    )
    val orderResponseWithEmptyEdge: List<GetMyOrdersQuery.Edge?> =
        listOf(null, generateOrderEdge(order1))
    val wrappedOrdersWithEmptyEdge =
        orderResponseWithEmptyEdge.map { it?.fragments?.fragmentOrder?.asModel }



    private fun generateOrderEdge(order: FragmentOrder) =
        GetMyOrdersQuery.Edge(
            "", GetMyOrdersQuery.Edge.Fragments(
                order
            )
        )

    private fun generateOrder(orderId: String) =
        FragmentOrder(
            "",
            orderId,
            orderId,
            "",
            "",
            OrderStatus.IN_PROGRESS,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null,
            null
        )

}