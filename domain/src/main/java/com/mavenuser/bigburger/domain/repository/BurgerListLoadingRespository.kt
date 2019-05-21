package com.mavenuser.bigburger.domain.repository

import com.mavenuser.bigburger.domain.model.Burger
import io.reactivex.Single

interface BurgerListLoadingRespository {

    fun getBurgerList(): Single<List<Burger>>
}