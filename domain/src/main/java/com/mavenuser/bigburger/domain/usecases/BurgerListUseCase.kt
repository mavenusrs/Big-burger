package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.repository.BurgerListLoadingRespository
import com.mavenuser.bigburger.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class BurgerListUseCase @Inject constructor(private val burgerListLoadingRepository:
                                            BurgerListLoadingRespository) :
                                            ObservableUseCase<BurgerListState>
{
    /**
     * get The list of burger
     * @return Observable<BurgerListState> obsrvable of state returned
     */
    override fun execute(): Observable<BurgerListState> {
        return burgerListLoadingRepository.getBurgerList()
            .toObservable()
            .map { BurgerListState.DefaultState(it) as BurgerListState }
            .onErrorReturn { BurgerListState.ErrorState(it)  }
//            .startWith( BurgerListState.LoadingState)
            .doOnSubscribe { BurgerListState.LoadingState }


    }


}