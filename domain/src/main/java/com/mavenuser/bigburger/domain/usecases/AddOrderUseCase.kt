package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Order
import com.mavenuser.bigburger.domain.repository.OrderRepository
import com.mavenuser.bigburger.domain.usecases.base.ObservableUseCaseWithParameter
import com.mavenuser.bigburger.domain.usecases.base.SingleUseCaseWithParameter
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository) :
    ObservableUseCaseWithParameter<Order?,  Long> {


    override fun execute(parameter: Order?): Observable<Long> {
        return orderRepository.AddOrder(parameter).toObservable()

    }


}