package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Burger

/**
 *  burger list state, may be on future adding paging
 *  TODO add paging on the future
 */
sealed class BurgerListState {

    data class DefaultState(var data: List<Burger>): BurgerListState()

    data class ErrorState(val throwable: Throwable): BurgerListState()

    object LoadingState: BurgerListState()

}



