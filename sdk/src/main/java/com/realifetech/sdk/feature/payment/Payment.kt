package com.realifetech.sdk.feature.payment

import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.fragment.PaymentSourceEdge
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentSourceInput
import io.reactivex.Single

class Payment private constructor() {

    fun addPaymentSource(input: PaymentSourceInput): Single<Result<PaymentSource>> {
        val paymentRepo = PaymentProvider().providePaymentRepository()
        return paymentRepo.addPaymentSourceSingle(input)
    }

    fun getMyPaymentSources(pageSize: Int, page: Int?): Single<Result<PaymentSourceEdge>> {
        val paymentRepo = PaymentProvider().providePaymentRepository()
        return paymentRepo.getMyPaymentSourcesSingle(pageSize, page)
    }

    fun createPaymentIntent(input: PaymentIntentInput): Single<Result<PaymentIntent>> {
        val paymentRepo = PaymentProvider().providePaymentRepository()
        return paymentRepo.createPaymentIntentSingle(input)
    }

    fun updatePaymentIntent(
        id: String,
        input: PaymentIntentInput
    ): Single<Result<PaymentIntent>> {
        val paymentRepo = PaymentProvider().providePaymentRepository()
        return paymentRepo.updatePaymentIntentSingle(id, input)
    }

    private object Holder {
        val instance = Payment()
    }

    companion object {
        val instance: Payment by lazy { Holder.instance }
    }
}