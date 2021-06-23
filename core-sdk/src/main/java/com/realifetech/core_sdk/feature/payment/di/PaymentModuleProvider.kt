package com.realifetech.core_sdk.feature.payment.di

import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.feature.payment.data.PaymentDataSource

object PaymentModuleProvider {
    fun providePaymentModule(): PaymentRepository {
        return PaymentRepository(PaymentDataSource())
    }
}