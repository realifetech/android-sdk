package com.realifetech.core_sdk.feature.payment

import com.realifetech.core_sdk.domain.Result
import com.realifetech.fragment.PaymentIntent
import com.realifetech.fragment.PaymentSource
import com.realifetech.fragment.PaymentSourceEdge
import com.realifetech.type.PaymentIntentInput
import com.realifetech.type.PaymentSourceInput
import io.reactivex.Single
import kotlinx.coroutines.rx2.rxSingle

class PaymentRepository(private val dataSource: DataSource) {

    suspend fun addPaymentSource(input: PaymentSourceInput) = dataSource.addPaymentSource(input)

    fun addPaymentSourceSingle(input: PaymentSourceInput): Single<Result<PaymentSource>> {
        return rxSingle { dataSource.addPaymentSource(input) }
    }

    suspend fun getMyPaymentSources(pageSize: Int, page: Int?) =
        dataSource.getMyPaymentSources(pageSize, page)

    fun getMyPaymentSourcesSingle(pageSize: Int, page: Int?): Single<Result<PaymentSourceEdge>> {
        return rxSingle { dataSource.getMyPaymentSources(pageSize, page) }
    }

    suspend fun createPaymentIntent(input: PaymentIntentInput) =
        dataSource.createPaymentIntent(input)

    fun createPaymentIntentSingle(input: PaymentIntentInput): Single<Result<PaymentIntent>> {
        return rxSingle { dataSource.createPaymentIntent(input) }
    }

    suspend fun updatePaymentIntent(id: String, input: PaymentIntentInput) =
        dataSource.updatePaymentIntent(id, input)

    fun updatePaymentIntentSingle(
        id: String,
        input: PaymentIntentInput
    ): Single<Result<PaymentIntent>> {
        return rxSingle { dataSource.updatePaymentIntent(id, input) }
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