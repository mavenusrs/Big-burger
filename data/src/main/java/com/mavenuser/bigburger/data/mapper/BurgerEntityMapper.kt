package com.mavenuser.bigburger.data.mapper

import com.mavenuser.bigburger.data.local.entity.BurgerResponse
import com.mavenuser.bigburger.domain.model.Burger

class BurgerEntityMapper {

    fun mapBurgersToListofBurgerResponse(burgers: Array<Burger>): Array<BurgerResponse>{
        return burgers.map {
            mapBurgerToBurgerResponse(it)
        }.toTypedArray()

    }
    fun mapBurgerToBurgerResponse(burger: Burger): BurgerResponse{
        return burger.let {
            BurgerResponse(it.id, it.ref, it.title, it.description, it.thumbnail, it.price
            , it.count, it.orderId, it.instructions)
        }

    }

    fun mapBurgerList(burgerResponseList: List<BurgerResponse>) : List<Burger>{
        return burgerResponseList.map{ it.mapModel()}
    }

    private fun BurgerResponse.mapModel(): Burger {
        return Burger(
            null, ref, title,
            description, thumbnail, price,
            count, order_id, instructions?.toString()?:""
        )
    }

}

