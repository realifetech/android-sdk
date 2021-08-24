package com.realifetech.sdk.sell.payment.di

import com.realifetech.sdk.sell.payment.domain.PaymentRepository
import com.realifetech.sdk.sell.payment.data.PaymentDataSource
import com.realifetech.sdk.core.network.graphQl.GraphQlModule

object PaymentModuleProvider {
    fun providePaymentModule(): PaymentRepository {
        return PaymentRepository(PaymentDataSource(GraphQlModule.apolloClient))
    }
}