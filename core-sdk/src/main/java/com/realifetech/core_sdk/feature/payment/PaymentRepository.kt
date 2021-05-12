package com.realifetech.core_sdk.feature.payment

import com.realifetech.core_sdk.domain.Result
import com.realifetech.core_sdk.feature.payment.data.PaymentIntentWrapper
import com.realifetech.core_sdk.feature.payment.data.PaymentSourceWrapper
import com.realifetech.core_sdk.feature.payment.data.toInputObject
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.fragment.PaymentSourceEdge
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentSourceInput
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle

class PaymentRepository(private val dataSource: DataSource) {

    suspend fun addPaymentSource(input: PaymentSourceWrapper) =
        dataSource.addPaymentSource(input.toInputObject())

    fun addPaymentSourceSingle(input: PaymentSourceWrapper): Single<Result<PaymentSource>> {
        return rxSingle { dataSource.addPaymentSource(input.toInputObject()) }
    }

    suspend fun getMyPaymentSources(pageSize: Int, page: Int?) =
        dataSource.getMyPaymentSources(pageSize, page)

    fun getMyPaymentSourcesSingle(pageSize: Int, page: Int?): Single<Result<PaymentSourceEdge>> {
        return rxSingle { dataSource.getMyPaymentSources(pageSize, page) }
    }

    suspend fun createPaymentIntent(input: PaymentIntentWrapper) =
        dataSource.createPaymentIntent(input.toInputObject())

    fun createPaymentIntentSingle(input: PaymentIntentWrapper): Single<Result<PaymentIntent>> {
        return rxSingle { dataSource.createPaymentIntent(input.toInputObject()) }
    }

    suspend fun updatePaymentIntent(id: String, input: PaymentIntentWrapper) =
        dataSource.updatePaymentIntent(id, input.toInputObject())

    fun updatePaymentIntentSingle(
        id: String,
        input: PaymentIntentWrapper
    ): Single<Result<PaymentIntent>> {
        return rxSingle { dataSource.updatePaymentIntent(id, input.toInputObject()) }
    }

    interface DataSource {
        suspend fun addPaymentSource(input: PaymentSourceInput): Result<PaymentSource>
        suspend fun getMyPaymentSources(pageSize: Int, page: Int?): Result<PaymentSourceEdge>
        suspend fun createPaymentIntent(input: PaymentIntentInput): Result<PaymentIntent>
        suspend fun updatePaymentIntent(
            id: String,
            input: PaymentIntentInput
        ): Result<PaymentIntent>
    }
}