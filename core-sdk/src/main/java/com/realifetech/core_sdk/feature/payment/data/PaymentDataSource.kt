package com.realifetech.core_sdk.feature.payment.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
import com.realifetech.AddPaymentSourceToMyWalletMutation
import com.realifetech.CreatePaymentIntentMutation
import com.realifetech.GetMyPaymentSourcesQuery
import com.realifetech.UpdatePaymentIntentMutation
import com.realifetech.core_sdk.data.payment.model.asModel
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.network.graphQl.GraphQlModule
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentIntentUpdateInput
import com.realifetech.type.PaymentSourceInput

class PaymentDataSource() : PaymentRepository.DataSource {
    private val apolloClient = GraphQlModule.apolloClient

    override fun addPaymentSource(
        input: PaymentSourceInput,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(AddPaymentSourceToMyWalletMutation(input))
            response.enqueue(object :
                ApolloCall.Callback<AddPaymentSourceToMyWalletMutation.Data>() {
                override fun onResponse(response: Response<AddPaymentSourceToMyWalletMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.addPaymentSourceToMyWallet?.fragments?.paymentSource
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

    override fun getMyPaymentSources(
        pageSize: Int,
        page: Int?,
        callback: (error: Exception?, response: PaginatedObject<com.realifetech.core_sdk.data.payment.model.PaymentSource?>?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(GetMyPaymentSourcesQuery(pageSize, Input.fromNullable(page)))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetMyPaymentSourcesQuery.Data>() {
                override fun onResponse(response: Response<GetMyPaymentSourcesQuery.Data>) {
                    val paymentSources =
                        response.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.edges?.map { it?.asModel }
                    val nextPage =
                        response.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.nextPage
                    val paymentPaginatedObject = PaginatedObject(paymentSources, nextPage)
                    callback.invoke(null, paymentPaginatedObject)
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun createPaymentIntent(
        input: PaymentIntentInput,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(CreatePaymentIntentMutation(input))
            response.enqueue(object : ApolloCall.Callback<CreatePaymentIntentMutation.Data>() {
                override fun onResponse(response: Response<CreatePaymentIntentMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.createPaymentIntent?.fragments?.paymentIntent
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

    override fun updatePaymentIntent(
        id: String,
        input: PaymentIntentUpdateInput,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(UpdatePaymentIntentMutation(id, input))
            response.enqueue(object : ApolloCall.Callback<UpdatePaymentIntentMutation.Data>() {
                override fun onResponse(response: Response<UpdatePaymentIntentMutation.Data>) {
                    callback.invoke(
                        null,
                        response.data?.updateMyPaymentIntent?.fragments?.paymentIntent
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