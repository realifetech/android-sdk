package com.realifetech.core_sdk.feature.payment.di

import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.feature.payment.data.PaymentDataSource
import com.realifetech.core_sdk.network.graphQl.GraphQlModule

object PaymentModuleProvider {
    fun providePaymentModule(baseUrl: String): PaymentRepository {
        val client = GraphQlModule.getApolloClient(baseUrl)
        return PaymentRepository(PaymentDataSource(client))
    }
}