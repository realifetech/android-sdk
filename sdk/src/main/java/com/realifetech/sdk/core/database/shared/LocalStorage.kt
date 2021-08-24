package com.realifetech.sdk.core.database.shared

import io.reactivex.Maybe
import io.reactivex.Single

interface LocalStorage<Entity, Model> {
    fun getAllItems(): Single<List<Entity>>
    fun getItem(id: String): Maybe<Entity>
    fun addAllItems(items: List<Entity>)
    fun addItem(item: Entity)
    fun removeItem(itemId: String): Entity?
}