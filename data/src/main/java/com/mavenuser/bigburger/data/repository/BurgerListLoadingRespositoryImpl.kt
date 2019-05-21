package com.mavenuser.bigburger.data.repository

import com.mavenuser.bigburger.data.api.BurgerListApi
import com.mavenuser.bigburger.data.mapper.BurgerEntityMapper
import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import io.reactivex.Single

class BurgerListLoadingRespositoryImpl(val burgerListApi: BurgerListApi,
                                       val burgerEntityMapper: BurgerEntityMapper) :
                        BurgerListLoadingRespository{

    override fun getBurgerList(): Single<List<Burger>> {
        return burgerListApi.getBurgerList().map{
            burgerEntityMapper.mapBurgerList(it)
            }

    }

}