package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import io.reactivex.Observable
import javax.inject.Inject

class BurgerListUseCase @Inject constructor(private val burgerListLoadingRepository: BurgerListLoadingRespository){

    /**
     * get The list of burger
     * @return Observable<BurgerListState> obsrvable of state returned
     */
    fun getWholeBurgerList(): Observable<BurgerListState>{
        return burgerListLoadingRepository.getBurgerList()
            .toObservable()
            .map { BurgerListState.DefaultState(it) as BurgerListState }
            .onErrorReturn { BurgerListState.ErrorState(it)  }
            .startWith( BurgerListState.LoadingState)

    }

}