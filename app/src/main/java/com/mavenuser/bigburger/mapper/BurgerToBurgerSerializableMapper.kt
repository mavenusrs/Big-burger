package com.mavenuser.bigburger.mapper

import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.model.BurgerSerializable
import javax.inject.Inject

class BurgerToBurgerSerializableMapper @Inject constructor() {
    fun map(listOfBurger: List<Burger>): List<BurgerSerializable> {
        return listOfBurger.map { it.mapp() }
    }


}

private fun Burger.mapp(): BurgerSerializable {
    return BurgerSerializable(ref, title, description, thumbnail, price)
}
