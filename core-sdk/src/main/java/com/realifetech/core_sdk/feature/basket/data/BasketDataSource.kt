package com.realifetech.core_sdk.feature.basket.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.*
import com.realifetech.core_sdk.data.basket.model.Basket
import com.realifetech.core_sdk.data.basket.model.asModel
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.order.model.asModel
import com.realifetech.core_sdk.data.shared.`object`.StandardResponse
import com.realifetech.core_sdk.data.shared.`object`.asModel
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.basket.BasketRepository
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.fragment.FragmentMutationResponse
import com.realifetech.type.BasketInput
import com.realifetech.type.CheckoutInput

class BasketDataSource() :
    BasketRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override suspend fun getBasket(): Result<Basket> {
        return try {
            val response = apolloClient.query(GetMyBasketQuery())
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
                .await()
            response.data?.getMyBasket?.fragments?.fragmentBasket?.asModel
                .extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun createMyBasket(basketInput: BasketInput): Result<Basket> {
        return try {
            val response =
                apolloClient.mutate(CreateMyBasketMutation(Input.optional(basketInput))).await()
            response.data?.createMyBasket?.fragments?.fragmentBasket?.asModel.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun updateMyBasket(basketInput: BasketInput): Result<Basket> {
        return try {
            val response =
                apolloClient.mutate(UpdateMyBasketMutation(Input.optional(basketInput))).await()
            response.data?.updateMyBasket?.fragments?.fragmentBasket?.asModel.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun deleteMyBasket(): Result<StandardResponse> {
        return try {
            val response = apolloClient.mutate(DeleteMyBasketMutation()).await()
            FragmentMutationResponse(
                success = response.data?.deleteMyBasket?.success ?: false,
                message = response.data?.deleteMyBasket?.message
            ).asModel.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun checkoutMyBasket(checkoutInput: CheckoutInput): Result<Order> {
        return try {
            val response =
                apolloClient.mutate(CheckoutMyBasketMutation(Input.optional(checkoutInput))).await()
            response.data?.checkoutMyBasket?.fragments?.fragmentOrder?.asModel.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }
}