package com.realifetech.sdk.sell.payment.data

import com.apollographql.apollo.ApolloCall
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.Response
import com.apollographql.apollo.exception.ApolloException
import com.apollographql.apollo.exception.ApolloHttpException
import com.apollographql.apollo.fetcher.ApolloResponseFetchers
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


    override fun addPaymentSource(
        input: PaymentSourceInput,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(AddPaymentSourceToMyWalletMutation(input))
            response.enqueue(object :
                ApolloCall.Callback<AddPaymentSourceToMyWalletMutation.Data>() {
                override fun onResponse(response: Response<AddPaymentSourceToMyWalletMutation.Data>) {
                    response.data?.addPaymentSourceToMyWallet?.fragments?.fragmentPaymentSource?.let {
                        callback.invoke(
                            null,
                            it.asModel
                        )
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }
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
        callback: (error: Exception?, response: PaginatedObject<PaymentSource?>?) -> Unit
    ) {
        try {
            val response =
                apolloClient.query(GetMyPaymentSourcesQuery(pageSize, Input.fromNullable(page)))
                    .toBuilder()
                    .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                    .build()
            response.enqueue(object : ApolloCall.Callback<GetMyPaymentSourcesQuery.Data>() {
                override fun onResponse(response: Response<GetMyPaymentSourcesQuery.Data>) {
                    response.data?.getMyPaymentSources?.fragments?.paymentSourceEdge?.let { sourceEdge ->
                        callback.invoke(
                            null,
                            PaginatedObject(
                                sourceEdge.edges?.map { it?.asModel },
                                sourceEdge.nextPage
                            )
                        )
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }

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
                    response.data?.createPaymentIntent?.fragments?.paymentIntent?.let {
                        callback.invoke(null, it)
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }
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
                    response.data?.updateMyPaymentIntent?.fragments?.paymentIntent?.let {
                        callback.invoke(null, it)
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }

            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun getMyPaymentIntent(
        id: String,
        callback: (error: Exception?, response: PaymentIntent?) -> Unit
    ) {
        try {
            val response = apolloClient.query(GetMyPaymentIntentQuery(id))
                .toBuilder()
                .responseFetcher(ApolloResponseFetchers.NETWORK_ONLY)
                .build()
            response.enqueue(object :
                ApolloCall.Callback<GetMyPaymentIntentQuery.Data>() {
                override fun onResponse(response: Response<GetMyPaymentIntentQuery.Data>) {
                    response.data?.getMyPaymentIntent?.fragments?.paymentIntent?.let {
                        callback.invoke(null, it)
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }
                }

                override fun onFailure(e: ApolloException) {
                    callback.invoke(e, null)
                }
            })
        } catch (exception: ApolloHttpException) {
            callback.invoke(exception, null)
        }
    }

    override fun deleteMyPaymentSource(
        id: String,
        callback: (error: Exception?, paymentSource: PaymentSource?) -> Unit
    ) {
        try {
            val response = apolloClient.mutate(DeleteMyPaymentSourceMutation(id))
            response.enqueue(object :
                ApolloCall.Callback<DeleteMyPaymentSourceMutation.Data>() {
                override fun onResponse(response: Response<DeleteMyPaymentSourceMutation.Data>) {
                    response.data?.deleteMyPaymentSource?.fragments?.fragmentPaymentSource?.let {
                        callback.invoke(null, it.asModel)
                    } ?: run {
                        callback.invoke(Exception(), null)
                    }
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