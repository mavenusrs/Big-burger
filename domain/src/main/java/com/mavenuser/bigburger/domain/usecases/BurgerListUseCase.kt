package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import io.reactivex.Single
import javax.inject.Inject

class BurgerListUseCase @Inject constructor(private val burgerListLoadingRepository: BurgerListLoadingRespository){

    /**
     * get The list of burger
     * @return Single<List<Burger>> list of burger emitted
     */
    fun getWholeBurgerList(): Single<List<Burger>>{
        return burgerListLoadingRepository.getBurgerList().map {burgerList ->
            burgerList.map {burger->
                    burger.price /= 100
                    burger }
        }
    }

}