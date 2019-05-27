package com.mavenuser.bigburger.domain.repository

import com.mavenuser.bigburger.domain.model.Burger
import io.reactivex.Completable
import io.reactivex.Single

interface BurgerListLoadingRespository {

    fun getBurgerList(): Single<List<Burger>>

    fun getOrdersBurgerList(orderId: Long): Single<List<Burger>>

    fun insertBurger(burger: Burger): Completable

    fun deleteBurger(burgers: Array<Burger>): Completable

    fun updateBurger(burger: Burger): Completable

}