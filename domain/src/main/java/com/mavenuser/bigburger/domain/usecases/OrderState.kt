package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Order

/**
 *  burger list state, may be on future adding paging
 *  TODO add paging on the future
 */
sealed class OrderState {

    data class DefaultState(var data: Order): OrderState()

    data class ErrorState(val throwable: Throwable): OrderState()

    object LoadingState: OrderState()

}



