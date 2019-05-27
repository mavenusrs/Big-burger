package com.mavenuser.bigburger.data.repository

import com.mavenuser.bigburger.data.api.BurgerListApi
import com.mavenuser.bigburger.data.local.dao.BurgerDAO
import com.mavenuser.bigburger.data.mapper.BurgerEntityMapper
import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import io.reactivex.Completable
import io.reactivex.Single

class BurgerListLoadingRespositoryImpl(val burgerListApi: BurgerListApi,
                                       val burgerDao : BurgerDAO,
                                       val burgerEntityMapper: BurgerEntityMapper) :
                                        BurgerListLoadingRespository{

    override fun getOrdersBurgerList(orderId: Long): Single<List<Burger>> {
        return burgerDao.getAllBurgers(orderId).map {
            burgerEntityMapper.mapBurgerList(it)
        }
    }

    override fun insertBurger(burger: Burger): Completable {
        return burgerDao.insertBurger(burgerEntityMapper.mapBurgerToBurgerResponse(burger))
    }

    override fun deleteBurger(burgers: Array<Burger>): Completable {
        return burgerDao.deleteBurger(burgerEntityMapper.mapBurgersToListofBurgerResponse(burgers))
    }

    override fun updateBurger(burger: Burger): Completable {
        return burgerDao.updateBurger(burgerEntityMapper.mapBurgerToBurgerResponse(burger))
    }

    override fun getBurgerList(): Single<List<Burger>> {
        return burgerListApi.getBurgerList().map{
            burgerEntityMapper.mapBurgerList(it)
            }

    }

}