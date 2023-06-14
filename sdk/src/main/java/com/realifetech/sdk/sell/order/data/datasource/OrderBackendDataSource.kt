package com.realifetech.sdk.sell.order.data.datasource

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.realifetech.GetMyOrderByIdQuery
import com.realifetech.GetMyOrdersQuery
import com.realifetech.UpdateMyOrderMutation
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.OrderUpdateInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderBackendDataSource @Inject constructor(private val apolloClient: ApolloClient) :
    OrderDataSource {
    override suspend fun getOrders(
        pageSize: Int,
        page: Int
    ): PaginatedObject<Order?> {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetMyOrdersQuery(pageSize, Optional.presentIfNotNull(page))
                val response = apolloClient.query(query).execute()
                if (response.errors?.isNotEmpty() == true) {
                    throw ApolloException(response.errors?.firstOrNull()?.message ?: "Unknown error")
                } else {
                    response.data?.getMyOrders?.let {
                        val orders = it.edges?.mapNotNull { it?.fragmentOrder?.asModel }
                        PaginatedObject(orders, it.nextPage)
                    } ?: PaginatedObject(emptyList(), null)
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun getOrderById(id: String): Order {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetMyOrderByIdQuery(id)
                val response = apolloClient.query(query).execute()
                if (response.errors?.isNotEmpty() == true) {
                    throw ApolloException(response.errors?.firstOrNull()?.message ?: "Unknown error")
                } else {
                    response.data?.getMyOrder?.fragmentOrder?.asModel ?: throw ApolloException("Order not found")
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun updateMyOrder(
        id: String,
        orderUpdateInput: OrderUpdateInput
    ): Order {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = UpdateMyOrderMutation(
                    id,
                    Optional.presentIfNotNull(orderUpdateInput)
                )
                val response = apolloClient.mutation(mutation).execute()
                if (response.errors?.isNotEmpty() == true) {
                    throw ApolloException(response.errors?.firstOrNull()?.message ?: "Unknown error")
                } else {
                    response.data?.updateMyOrder?.fragmentOrder?.asModel ?: throw ApolloException("Update failed")
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

}