package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Order
import com.mavenuser.bigburger.domain.repository.OrderRepository
import com.mavenuser.bigburger.domain.usecases.base.CompletableUseCaseWithParameter
import io.reactivex.Completable
import javax.inject.Inject

class DeleteOrderUseCase @Inject constructor(
    private val orderRepository: OrderRepository) :
    CompletableUseCaseWithParameter<Order> {


    override fun execute(parameter: Order): Completable {
        return orderRepository.deleteOrder(parameter)
            .doOnComplete { CompletableState.CompleteState }
            .doOnError { CompletableState.ErrorState(it) }
            .doOnSubscribe { CompletableState.LoadingState }
            .doOnComplete { CompletableState.HidingState }
    }


}