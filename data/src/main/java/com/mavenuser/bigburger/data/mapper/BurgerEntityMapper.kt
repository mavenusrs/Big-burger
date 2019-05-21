package com.mavenuser.bigburger.data.mapper

import com.mavenuser.bigburger.data.entity.BurgerResponse
import com.mavenuser.bigburger.domain.model.Burger

class BurgerEntityMapper {

    fun mapBurgerList(burgerResponseList: List<BurgerResponse>) : List<Burger>{
        return burgerResponseList.map{ mapModel(it)}
    }

    private fun mapModel(burgerResponse: BurgerResponse): Burger {
        return Burger(burgerResponse.ref, burgerResponse.title,
                    burgerResponse.description, burgerResponse.thumbnail, burgerResponse.price)
    }
}