package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.AddPaymentSourceToMyWalletMutation
import com.realifetech.CreatePaymentIntentMutation
import com.realifetech.GetMyPaymentSourcesQuery
import com.realifetech.UpdatePaymentIntentMutation
import com.realifetech.core_sdk.data.payment.model.asModel
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput

class PaymentDataSource() : PaymentRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override suspend fun addPaymentSource(input: PaymentSourceInput): Result<PaymentSource> {
        return try {
            val response = apolloClient.mutate(AddPaymentSourceToMyWalletMutation(input)).await()
            response.data?.addPaymentSourceToMyWallet?.fragments?.paymentSource.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun getMyPaymentSources(
        pageSize: Int,
        page: Int?
    ): Result<PaginatedObject<com.realifetech.core_sdk.data.payment.model.PaymentSource?>> {
        return try {
            val response =
                apolloClient.query(GetMyPaymentSourcesQuery(pageSize, Input.fromNullable(page)))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                    .build()
                    .await()
            val paymentSources =
                response.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.edges?.map { it?.asModel }
            val nextPage =
                response.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.nextPage
            val paymentPaginatedObject = PaginatedObject(paymentSources, nextPage)
            paymentPaginatedObject.extractResponse(
                response.errors
            )
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun createPaymentIntent(input: PaymentIntentInput): Result<PaymentIntent> {
        return try {
            val response = apolloClient.mutate(CreatePaymentIntentMutation(input)).await()
            response.data?.createPaymentIntent?.fragments?.paymentIntent.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

    override suspend fun updatePaymentIntent(
        id: String,
        input: PaymentIntentUpdateInput
    ): Result<PaymentIntent> {
        return try {
            val response = apolloClient.mutate(UpdatePaymentIntentMutation(id, input)).await()
            response.data?.updateMyPaymentIntent?.fragments?.paymentIntent.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

}