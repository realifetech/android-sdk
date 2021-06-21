package com.realifetech.sdk.sell.payment

import com.realifetech.core_sdk.feature.payment.PaymentRepository
import com.realifetech.core_sdk.feature.payment.di.PaymentModuleProvider
import com.realifetech.sdk.general.General

internal class PaymentProvider() {
    fun providePaymentRepository(): PaymentRepository {
        return PaymentModuleProvider.providePaymentModule(General.instance.configuration.graphApiUrl)
    }
}