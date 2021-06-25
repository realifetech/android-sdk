package com.realifetech.core_sdk.feature.order

import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.data.order.wrapper.OrderUpdateWrapper
import com.realifetech.core_sdk.data.shared.`object`.PaginatedObject
import com.realifetech.core_sdk.database.order.OrdersSharedPreferencesManager
import com.realifetech.core_sdk.domain.CoreConfiguration

class OrderRepository(private val dataSource: DataSource) {

    private val localStorageManager = OrdersSharedPreferencesManager(CoreConfiguration.context)

    fun getOrders(
        pageSize: Int,
        page: Int,
        callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
    ) {
        dataSource.getOrders(pageSize, page, callback)
    }

    fun getOrderById(id: String, callback: (error: Exception?, order: Order?) -> Unit) {
        dataSource.getOrderById(id, callback)
//        val cachedValue =
//            localStorageManager.getItem(id)
//                .flatMapSingle<Result<Order>> { Single.just(Result.Success(it.asModel)) }
//        rxSingle { dataSource.getOrderById(id, callback) }.doAfterSuccess {
//            localStorageManager.removeItem(id)
//            localStorageManager.addItem(it.asEntity)
//        }.onErrorResumeNext(cachedValue)
    }

    fun updateMyOrder(
        id: String,
        input: OrderUpdateWrapper,
        callback: (error: Exception?, order: Order?) -> Unit
    ) {
        dataSource.updateMyOrder(id, input, callback)
//        rxSingle { dataSource.updateMyOrder(id, input, callback) }.doAfterSuccess {
//            localStorageManager.removeItem(id)
//            when (it) {
//                is Result.Success -> localStorageManager.addItem(it.data.asEntity)
//            }
//        }
    }

    interface DataSource {
        fun getOrders(
            pageSize: Int,
            page: Int,
            callback: (error: Exception?, response: PaginatedObject<Order?>?) -> Unit
        )

        fun getOrderById(id: String, callback: (error: Exception?, order: Order?) -> Unit)
        fun updateMyOrder(
            id: String,
            orderUpdateWrapper: OrderUpdateWrapper,
            callback: (error: Exception?, order: Order?) -> Unit
        )
    }
}