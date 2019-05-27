package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import com.mavenuser.bigburger.domain.usecases.base.CompletableUseCaseWithParameter
import io.reactivex.Completable
import javax.inject.Inject

class AddItemToOrderUseCase @Inject constructor(
    private val burgerListLoadingRepository:
    BurgerListLoadingRespository) :
    CompletableUseCaseWithParameter<Burger> {


    override fun execute(parameter: Burger): Completable {
        return burgerListLoadingRepository.insertBurger(parameter)
    }



}