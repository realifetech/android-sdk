package com.realifetech.sdk.sell.payment

import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.feature.payment.di.PaymentModuleProvider

internal class PaymentProvider() {
    fun providePaymentRepository(): PaymentRepository {
        return PaymentModuleProvider.providePaymentModule()
    }
}