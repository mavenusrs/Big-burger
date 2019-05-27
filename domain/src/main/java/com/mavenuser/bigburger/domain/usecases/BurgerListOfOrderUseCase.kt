package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import com.mavenuser.bigburger.domain.usecases.base.ObservableUseCaseWithParameter
import io.reactivex.Observable
import javax.inject.Inject

class BurgerListOfOrderUseCase @Inject constructor(
    private val burgerListLoadingRepository:
    BurgerListLoadingRespository) :
    ObservableUseCaseWithParameter<Long, BurgerListState> {


    /**
     * get The list of burger
     * @return Observable<BurgerListState> obsrvable of state returned
     */
    override fun execute(parameter : Long): Observable<BurgerListState> {
        return burgerListLoadingRepository.getOrdersBurgerList(parameter)
            .toObservable()
            .map { BurgerListState.DefaultState(it) as BurgerListState }
            .onErrorReturn { BurgerListState.ErrorState(it) }
            .startWith(BurgerListState.LoadingState)
    }


}