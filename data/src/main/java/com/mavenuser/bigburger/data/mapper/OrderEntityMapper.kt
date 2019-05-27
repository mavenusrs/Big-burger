package com.mavenuser.bigburger.data.mapper

import com.mavenuser.bigburger.data.local.entity.OrderEntity
import com.mavenuser.bigburger.domain.model.Order
import javax.inject.Inject

class OrderEntityMapper @Inject constructor(){

    fun mapOrderEntityToOrder(orderEntity: OrderEntity): Order {
        return Order(orderEntity.id, orderEntity.current_order)
    }

    fun mapOrderToOrderEntity(order: Order): OrderEntity {
        return OrderEntity(order.id, order.current_order)
    }

}