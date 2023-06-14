package com.realifetech.sdk.sell.basket.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloException
import com.apollographql.apollo3.exception.ApolloHttpException
import com.realifetech.*
import com.realifetech.sdk.core.data.model.basket.Basket
import com.realifetech.sdk.core.data.model.basket.asModel
import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.StandardResponse
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import javax.inject.Inject

class BasketDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    BasketDataSource {

    override suspend fun getBasket(): Basket? {
        return withContext(Dispatchers.IO) {
            try {
                val query = GetMyBasketQuery()
                val response = apolloClient.query(query).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.getMyBasket?.fragmentBasket?.asModel
                }
            } catch (exception: HttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun createMyBasket(basketInput: BasketInput): Basket? {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = CreateMyBasketMutation(Optional.Present(basketInput))
                val response = apolloClient.mutation(mutation).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.createMyBasket?.fragmentBasket?.asModel
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun updateMyBasket(basketInput: BasketInput): Basket? {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = UpdateMyBasketMutation(Optional.Present(basketInput))
                val response = apolloClient.mutation(mutation).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.updateMyBasket?.fragmentBasket?.asModel
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun deleteMyBasket(): StandardResponse {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = DeleteMyBasketMutation()
                val response = apolloClient.mutation(mutation).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    StandardResponse(
                        message = response.data?.deleteMyBasket?.message,
                        code = null,
                        type = null
                    )
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }

    override suspend fun checkoutMyBasket(checkoutInput: CheckoutInput): Order? {
        return withContext(Dispatchers.IO) {
            try {
                val mutation = CheckoutMyBasketMutation(Optional.Present(checkoutInput))
                val response = apolloClient.mutation(mutation).execute()

                if (response.hasErrors()) {
                    throw ApolloException(
                        response.errors?.firstOrNull()?.message ?: "Unknown error"
                    )
                } else {
                    response.data?.checkoutMyBasket?.fragmentOrder?.asModel
                }
            } catch (exception: ApolloHttpException) {
                throw Exception(exception)
            }
        }
    }
}