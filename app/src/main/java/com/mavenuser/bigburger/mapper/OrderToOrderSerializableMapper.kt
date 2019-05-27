package com.mavenuser.bigburger.mapper

import com.mavenuser.bigburger.domain.model.Order
import com.mavenuser.bigburger.model.OrderSerializable
import javax.inject.Inject

class OrderToOrderSerializableMapper @Inject constructor() {
    fun map(listOfBurger: List<Order>): List<OrderSerializable> {
        return listOfBurger.map { it.mapp() }
    }




    fun reverseMap(orderSerializable: OrderSerializable): Order {
        return Order(orderSerializable.id, orderSerializable.current_order)
    }
}

 fun Order.mapp(): OrderSerializable {
    return OrderSerializable(id, current_order)
}

