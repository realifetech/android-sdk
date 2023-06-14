package com.realifetech.sdk.sell.payment.data

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.Optional
import com.apollographql.apollo3.exception.ApolloHttpException
import com.realifetech.*
import com.realifetech.fragment.PaymentIntent
import com.realifetech.sdk.core.data.model.payment.model.PaymentSource
import com.realifetech.sdk.core.data.model.payment.model.asModel
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput
import javax.inject.Inject

class PaymentDataSourceImpl @Inject constructor(private val apolloClient: ApolloClient) :
    PaymentDataSource {

    override suspend fun addPaymentSource(input: PaymentSourceInput): PaymentSource? {
        return try {
            val response = apolloClient.mutate(AddPaymentSourceToMyWalletMutation(input)).execute()
            response.data?.addPaymentSourceToMyWallet?.fragmentPaymentSource?.asModel
        } catch (exception: ApolloHttpException) {
            null
        }
    }

    override suspend fun getMyPaymentSources(
        pageSize: Int,
        page: Int?
    ): PaginatedObject<PaymentSource?>? {
        return try {
            val response = apolloClient.query(GetMyPaymentSourcesQuery(pageSize, Optional.presentIfNotNull(page))).execute()
            response.data?.getMyPaymentSources?.paymentSourceEdge?.let { sourceEdge ->
                PaginatedObject(
                    sourceEdge.edges?.map { it?.asModel },
                    sourceEdge.nextPage
                )
            }
        } catch (exception: ApolloHttpException) {
            null
        }
    }

    override suspend fun createPaymentIntent(input: PaymentIntentInput): PaymentIntent? {
        return try {
            val response = apolloClient.mutation(CreateMyPaymentIntentMutation(input)).execute()
            response.data?.createMyPaymentIntent?.paymentIntent
        } catch (exception: ApolloHttpException) {
            null
        }
    }

    override suspend fun updatePaymentIntent(
        id: String,
        input: PaymentIntentUpdateInput
    ): PaymentIntent? {
        return try {
            val response = apolloClient.mutation(UpdatePaymentIntentMutation(id, input)).execute()
            response.data?.updateMyPaymentIntent?.paymentIntent
        } catch (exception: ApolloHttpException) {
            null
        }
    }

    override suspend fun getMyPaymentIntent(id: String): PaymentIntent? {
        return try {
            val response = apolloClient.query(GetMyPaymentIntentQuery(id)).execute()
            response.data?.getMyPaymentIntent?.paymentIntent
        } catch (exception: ApolloHttpException) {
            null
        }
    }

    override suspend fun deleteMyPaymentSource(id: String): PaymentSource? {
        return try {
            val response = apolloClient.mutation(DeleteMyPaymentSourceMutation(id)).execute()
            response.data?.deleteMyPaymentSource?.fragmentPaymentSource?.asModel
        } catch (exception: ApolloHttpException) {
            null
        }
    }
}