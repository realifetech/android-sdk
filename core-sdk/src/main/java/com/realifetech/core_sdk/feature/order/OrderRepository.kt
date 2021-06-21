package com.realifetech.core_sdk.feature.order

import android.content.Context
import com.realifetech.core_sdk.data.order.database.asEntity
import com.realifetech.core_sdk.data.order.database.asModel
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.order.wrapper.OrderUpdateWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.database.order.OrdersSharedPreferencesManager
import com.realifetech.core_sdk.domain.Result
import io.reactivex.Single
import kotlinx.coroutines.rx2.await
import kotlinx.coroutines.rx2.rxSingle

class OrderRepository(private val dataSource: DataSource, context: Context) {

    private val localStorageManager = OrdersSharedPreferencesManager(context)

    suspend fun getOrders(pageSize: Int, page: Int) = dataSource.getOrders(pageSize, page)

    suspend fun getOrderById(id: String): Result<Order> {
        val cachedValue =
            localStorageManager.getItem(id)
                .flatMapSingle<Result<Order>> { Single.just(Result.Success(it.asModel)) }

        return rxSingle { dataSource.getOrderById(id) }.doAfterSuccess {
            localStorageManager.removeItem(id)
            when (it) {
                is Result.Success -> localStorageManager.addItem(it.data.asEntity)
            }
        }.onErrorResumeNext(cachedValue).await()
    }

    suspend fun updateMyOrder(id: String, input: OrderUpdateWrapper): Result<Order> {
        return rxSingle { dataSource.updateMyOrder(id, input) }.doAfterSuccess {
            localStorageManager.removeItem(id)
            when (it) {
                is Result.Success -> localStorageManager.addItem(it.data.asEntity)
            }
        }.await()
    }

    interface DataSource {
        suspend fun getOrders(pageSize: Int, page: Int): Result<PaginatedObject<Order?>>
        suspend fun getOrderById(id: String): Result<Order>
        suspend fun updateMyOrder(id: String, orderUpdateWrapper: OrderUpdateWrapper): Result<Order>
    }
}