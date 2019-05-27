package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.repository.OrderRepository
import com.mavenuser.bigburger.domain.usecases.base.ObservableUseCase
import io.reactivex.Observable
import javax.inject.Inject

class GetOrderUseCase @Inject constructor(private val orderRepository:
                                            OrderRepository) :
                                            ObservableUseCase<OrderState>
{
    /**
     * get The list of burger
     * @return Observable<BurgerListState> obsrvable of state returned
     */
    override fun execute(): Observable<OrderState> {
        return orderRepository.getCurrentOrder()
            .toObservable()
            .map { OrderState.DefaultState(it) as OrderState }
            .onErrorReturn { OrderState.ErrorState(it)  }
            .startWith( OrderState.LoadingState)

    }


}