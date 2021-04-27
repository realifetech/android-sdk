package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.coroutines.await
import com.apollographql.apollo.exception.ApolloHttpException
import com.realifetech.AddPaymentSourceToMyWalletMutation
import com.realifetech.CreatePaymentIntentMutation
import com.realifetech.GetMyPaymentSourcesQuery
import com.realifetech.UpdatePaymentIntentMutation
import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.helper.extractResponse
import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.fragment.PaymentSourceEdge
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentSourceInput

class PaymentDataSource(private val apolloClient: ApolloClient) : PaymentRepository.DataSource {

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

    override suspend fun getMyPaymentSources(pageSize: Int, page: Int?): Result<PaymentSourceEdge> {
        return try {
            val response =
                apolloClient.query(GetMyPaymentSourcesQuery(pageSize, Input.fromNullable(page)))
                    .await()
            response.data?.getMyPaymentSources?.fragments?.paymentSourceEdge.extractResponse(
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
        input: PaymentIntentInput
    ): Result<PaymentIntent> {
        return try {
            val response = apolloClient.mutate(UpdatePaymentIntentMutation(id, input)).await()
            response.data?.updateMyPaymentIntent?.fragments?.paymentIntent.extractResponse(response.errors)
        } catch (exception: ApolloHttpException) {
            Result.Error(exception)
        }
    }

}