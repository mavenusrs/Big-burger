package com.mavenuser.bigburger.data.repository

import com.mavenuser.bigburger.data.local.OrderDAO
import com.mavenuser.bigburger.data.local.entity.OrderEntity
import com.mavenuser.bigburger.data.mapper.OrderEntityMapper
import com.mavenuser.bigburger.domain.model.Order
import com.mavenuser.bigburger.domain.repository.OrderRepository
import io.reactivex.Completable
import io.reactivex.Single

class OrderRepositoryImpl(val orderDAO : OrderDAO,
                          val orderMapper: OrderEntityMapper): OrderRepository {

    override fun getCurrentOrder(): Single<Order> {
        return orderDAO.getCurrentOrder().map {
            orderMapper.mapOrderEntityToOrder(it) }
           }

    override fun AddOrder(order: Order?): Single<Long> {
        return orderDAO.insertOrder(OrderEntity(order?.id, 1))
    }

//    override fun updateOrder(order: Order): Completable {
//        return orderDAO.updateOrder(orderMapper.mapOrderToOrderEntity(order))
//    }

    override fun deleteOrder(order: Order): Completable {
        return orderDAO.deleteOrder(orderMapper.mapOrderToOrderEntity(order))
    }
}