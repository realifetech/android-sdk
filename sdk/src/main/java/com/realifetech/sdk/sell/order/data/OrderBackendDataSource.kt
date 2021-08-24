package com.realifetech.sdk.sell.order.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyOrderByIdQuery
import com.realifetech.GetMyOrdersQuery
import com.realifetech.UpdateMyOrderMutation
import com.realifetech.sdk.core.data.order.model.Order
import com.realifetech.sdk.core.data.order.model.asModel
import com.realifetech.sdk.core.data.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.order.wrapper.asInput
import com.realifetech.sdk.core.data.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.domain.OrderRepository
import com.realifetech.sdk.core.network.graphQl.GraphQlModule

class OrderBackendDataSource() : OrderRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyOrdersQuery(pageSize, page.toInput()))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetMyOrdersQuery.Data>() {
                override fun onResponse(response: Response<GetMyOrdersQuery.Data>) {
                    val orders =
                        response.data?.getMyOrders?.edges?.map { result -> result?.fragments?.fragmentOrder?.asModel }
                    val nextPage = response.data?.getMyOrders?.nextPage
                    val paginatedObject = PaginatedObject(orders, nextPage)
                    callback.invoke(null, paginatedObject)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getOrderById(id: String, callback: (error: Exception?, order: Order?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyOrderByIdQuery(id))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.CACHE_AND_NETWORK)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetMyOrderByIdQuery.Data>() {
                override fun onResponse(response: Response<GetMyOrderByIdQuery.Data>) {
                    val order = response.data?.getMyOrder?.fragments?.fragmentOrder?.asModel
                    callback.invoke(null, order)
                }

                override fun onFailure(e: ApolloException) {
                    callback(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun updateMyOrder(
        id: String,
        orderUpdateWrapper: OrderUpdateWrapper,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(
                UpdateMyOrderMutation(
                    id,
                    orderUpdateWrapper.asInput
                )
            )
            response.enqueue(object : ApolloCall.Callback<UpdateMyOrderMutation.Data>() {
                override fun onResponse(response: Response<UpdateMyOrderMutation.Data>) {
                    val order = response.data?.updateMyOrder?.fragments?.fragmentOrder?.asModel
                    callback.invoke(null, order)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }
}