package com.realifetech.sdk.sell.payment

import com.realifetech.sdk.sell.payment.domain.PaymentRepository
import com.realifetech.sdk.sell.payment.di.PaymentModuleProvider

internal class PaymentProvider() {
    fun providePaymentRepository(): PaymentRepository {
        return PaymentModuleProvider.providePaymentModule()
    }
}