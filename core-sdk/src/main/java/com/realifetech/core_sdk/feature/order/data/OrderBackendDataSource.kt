package com.realifetech.core_sdk.feature.order.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.GetMyOrderByIdQuery
import com.realifetech.GetMyOrdersQuery
import com.realifetech.UpdateMyOrderMutation
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.order.model.asModel
import com.realifetech.core_sdk.data.order.wrapper.OrderUpdateWrapper
import com.realifetech.core_sdk.data.order.wrapper.asInput
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.feature.order.OrderRepository

class OrderBackendDataSource(private val apolloClient: ApolloClient) : OrderRepository.DataSource {
    override suspend fun getOrders(pageSize: Int, page: Int): Result<PaginatedObject<Order?>> {
        return try {
            val response = apolloClient.query(GetMyOrdersQuery(pageSize, page.toInput())).await()
            val orders =
                response.data?.getMyOrders?.edges?.map { result -> result?.fragments?.fragmentOrder?.asModel }
            val nextPage = response.data?.getMyOrders?.nextPage
            val paginatedObject = PaginatedObject(orders, nextPage)
            return paginatedObject.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getOrderById(id: String): Result<Order> {
        return try {
            val response = apolloClient.query(GetMyOrderByIdQuery(id)).await()
            val order = response.data?.getMyOrder?.fragments?.fragmentOrder?.asModel
            return order.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun updateMyOrder(
        id: String,
        orderUpdateWrapper: OrderUpdateWrapper
    ): Result<Order> {
        return try {
            val response = apolloClient.mutate(
                UpdateMyOrderMutation(
                    id,
                    orderUpdateWrapper.asInput
                )
            ).await()
            val order = response.data?.updateMyOrder?.fragments?.fragmentOrder?.asModel
            return order.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }
}