package com.mavenuser.bigburger.presentation.burgerList

import com.mavenuser.bigburger.model.BurgerSerializable

/**
 *  burger list state, may be on future adding paging
 *  TODO add paging on the future
 */
sealed class BurgerListState {

    data class DefaultState(var data: List<BurgerSerializable>): BurgerListState()

    data class ErrorState(val throwable: Throwable): BurgerListState()

    object LoadingState: BurgerListState()

}



