package com.mavenuser.bigburger.mapper

import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.model.BurgerSerializable
import javax.inject.Inject

class BurgerToBurgerSerializableMapper @Inject constructor() {
    fun map(listOfBurger: List<Burger>): List<BurgerSerializable> {
        return listOfBurger.map { it.mapp() }
    }


    fun inverseMapList(listOfBurger: List<BurgerSerializable>): List<Burger> {
        return listOfBurger.map { inverseMap(it) }
    }

    fun inverseMap(burgerSerializable: BurgerSerializable): Burger {
        burgerSerializable.apply {
            return Burger(id, ref, title, description, thumbnail, price, count, orderId , instructions )
        }
    }
}

private fun Burger.mapp(): BurgerSerializable {
    return BurgerSerializable(id, ref, title, description, thumbnail, price, count, orderId, instructions)
}
