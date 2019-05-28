package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Burger
import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import com.mavenuser.bigburger.domain.usecases.base.CompletableUseCaseWithParameter
import io.reactivex.Completable
import javax.inject.Inject

class DeleteItemsFromOrderUseCase @Inject constructor(
    private val burgerListLoadingRepository:
    BurgerListLoadingRespository) :
    CompletableUseCaseWithParameter<Array<Burger>> {


    override fun execute(parameter: Array<Burger>): Completable {
        return burgerListLoadingRepository.deleteBurger(parameter)
    }



}