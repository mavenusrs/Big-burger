package com.mavenuser.bigburger.domain.repository

import com.mavenuser.bigburger.domain.model.Order
import io.reactivex.Completable
import io.reactivex.Single

interface OrderRepository {

    fun getCurrentOrder(): Single<Order>

    fun AddOrder(order: Order?): Single<Long>

//    fun updateOrder(order: Order): Completable

    fun deleteOrder(order: Order): Completable
}