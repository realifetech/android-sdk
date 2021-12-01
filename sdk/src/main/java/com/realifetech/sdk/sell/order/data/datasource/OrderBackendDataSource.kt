package com.realifetech.sdk.sell.order.data.datasource

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.api.toInput
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.GetMyOrderByIdQuery
import com.realifetech.GetMyOrdersQuery
import com.realifetech.UpdateMyOrderMutation
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.model.order.wrapper.asInput
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import javax.inject.Inject

class OrderBackendDataSource @Inject constructor(private val apolloClient: ApolloClient) :
    OrderDataSource {

    override fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyOrdersQuery(pageSize, page.toInput()))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetMyOrdersQuery.Data>() {
                override fun onResponse(response: Response<GetMyOrdersQuery.Data>) {
                    response.data?.getMyOrders?.let {
                        callback.invoke(
                            null,
                            PaginatedObject(
                                it.edges?.map { result -> result?.fragments?.fragmentOrder?.asModel },
                                it.nextPage
                            )
                        )
                    } ?: run { callback.invoke(null, null) }
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
                .responseFetcher(ApolloResponseFetchers.NETWORK_FIRST)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetMyOrderByIdQuery.Data>() {
                override fun onResponse(response: Response<GetMyOrderByIdQuery.Data>) {
                    response.data?.getMyOrder?.fragments?.fragmentOrder?.let {
                        return callback.invoke(null, it.asModel)
                    } ?: run { callback.invoke(null, null) }

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
                    response.data?.updateMyOrder?.fragments?.fragmentOrder?.let {
                        callback.invoke(
                            null,
                            it.asModel
                        )
                    } ?: run { callback.invoke(null, null) }

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