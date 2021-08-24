package com.realifetech.sdk.sell.basket.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.*
import com.realifetech.sdk.core.data.basket.model.Basket
import com.realifetech.sdk.core.data.basket.model.asModel
import com.realifetech.sdk.core.data.order.model.Order
import com.realifetech.sdk.core.data.order.model.asModel
import com.realifetech.sdk.core.data.shared.`object`.StandardResponse
import com.realifetech.sdk.sell.basket.domain.BasketRepository
import com.realifetech.sdk.core.network.graphQl.GraphQlModule
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput

class BasketDataSource() :
    BasketRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override fun getBasket(callback: (error: Exception?, basket: Basket?) -> Unit) {
        try {
            val response = apolloClient.query(GetMyBasketQuery())
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
            response.enqueue(object : ApolloCall.Callback<GetMyBasketQuery.Data>() {
                override fun onResponse(response: Response<GetMyBasketQuery.Data>) {
                    callback.invoke(
                        null,
                        response.data?.getMyBasket?.fragments?.fragmentBasket?.asModel
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun createMyBasket(
        basketInput: BasketInput,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        try {
            val response =
                apolloClient.mutate(CreateMyBasketMutation(Input.optional(basketInput)))
            response.enqueue(object : ApolloCall.Callback<CreateMyBasketMutation.Data>() {
                override fun onResponse(response: Response<CreateMyBasketMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.createMyBasket?.fragments?.fragmentBasket?.asModel
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun updateMyBasket(
        basketInput: BasketInput,
        callback: (error: Exception?, basket: Basket?) -> Unit
    ) {
        try {
            val response =
                apolloClient.mutate(UpdateMyBasketMutation(Input.optional(basketInput)))
            response.enqueue(object : ApolloCall.Callback<UpdateMyBasketMutation.Data>() {
                override fun onResponse(response: Response<UpdateMyBasketMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.updateMyBasket?.fragments?.fragmentBasket?.asModel
                    )
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun deleteMyBasket(callback: (error: Exception?, response: StandardResponse?) -> Unit) {
        try {
            val response = apolloClient.mutate(DeleteMyBasketMutation())
            response.enqueue(object : ApolloCall.Callback<DeleteMyBasketMutation.Data>() {
                override fun onResponse(response: Response<DeleteMyBasketMutation.Data>) {
                    val standardResponse = StandardResponse(
                        message = response.data?.deleteMyBasket?.message,
                        code = null,
                        type = null
                    )
                    callback.invoke(null, standardResponse)
                }

                override fun onFailure(e: ApolloException) {
                    TODO("Not yet implemented")
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun checkoutMyBasket(
        checkoutInput: CheckoutInput,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        try {
            val response =
                apolloClient.mutate(CheckoutMyBasketMutation(Input.optional(checkoutInput)))
            response.enqueue(object : ApolloCall.Callback<CheckoutMyBasketMutation.Data>() {
                override fun onResponse(response: Response<CheckoutMyBasketMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.checkoutMyBasket?.fragments?.fragmentOrder?.asModel
                    )
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