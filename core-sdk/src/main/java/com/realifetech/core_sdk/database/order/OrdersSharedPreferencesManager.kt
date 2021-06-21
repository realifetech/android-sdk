package com.realifetech.core_sdk.database.order

import android.content.Context
import com.realifetech.core_sdk.data.order.database.OrderEntity
import com.realifetech.core_sdk.data.order.model.Order
import com.realifetech.core_sdk.database.shared.SharedPreferencesManager
import com.realifetech.core_sdk.database.shared.getListFromSharedPreferences
import com.realifetech.core_sdk.database.shared.saveListToSharedPreferences
import io.reactivex.Maybe
import io.reactivex.Single

class OrdersSharedPreferencesManager(private val context: Context) :
    OrdersLocalStorageManager,
    SharedPreferencesManager<OrderEntity, Order> {

    private val allOrders: List<OrderEntity>
        get() = getListFromSharedPreferences(ORDERS_LIST, context).orEmpty()

    override fun getAllItems(): Single<List<OrderEntity>> {
        return Single.just(allOrders)
    }

    override fun getItem(id: String): Maybe<OrderEntity> {
        return Maybe.create<OrderEntity> { emitter ->
            val targetOrder = allOrders.firstOrNull { it.orderId == id }
            if (targetOrder != null) {
                emitter.onSuccess(targetOrder)
            }
            emitter.onComplete()
        }
    }

    override fun addAllItems(items: List<OrderEntity>) {
        saveListToSharedPreferences(items, ORDERS_LIST, context)
    }

    override fun addItem(item: OrderEntity) {
        val resultList = allOrders + item
        addAllItems(resultList)
    }

    override fun removeItem(itemId: String): OrderEntity? {
        val newList = allOrders.toMutableList()
        val removedItem = newList.find { it.orderId == itemId }
        newList.remove(removedItem)

        addAllItems(newList)
        return removedItem
    }

    companion object {
        private const val ORDERS_LIST = "OrderList"
    }
}