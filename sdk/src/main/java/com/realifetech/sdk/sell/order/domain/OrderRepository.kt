package com.realifetech.sdk.sell.order.domain

import com.realifetech.sdk.core.data.model.order.model.Order
import com.realifetech.sdk.core.data.model.order.wrapper.OrderUpdateWrapper
import com.realifetech.sdk.core.data.model.shared.`object`.PaginatedObject
import com.realifetech.sdk.sell.order.data.database.OrdersSharedPreferencesManager
import com.realifetech.sdk.sell.order.data.datasource.OrderDataSource

class OrderRepository(
    private val dataSource: OrderDataSource,
    private val localStorageManager: OrdersSharedPreferencesManager
) {

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
}