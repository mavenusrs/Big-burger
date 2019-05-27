package com.mavenuser.bigburger.domain.usecases

import com.mavenuser.bigburger.domain.model.Order
import com.mavenuser.bigburger.domain.repository.OrderRepository
import com.mavenuser.bigburger.domain.usecases.base.CompletableUseCaseWithParameter
import io.reactivex.Completable
import javax.inject.Inject

//class UpdateOrderUseCase @Inject constructor(
//    private val orderRepository: OrderRepository) :
//    CompletableUseCaseWithParameter<Order> {
//
//
//    override fun execute(order: Order): Completable {
//        return orderRepository.updateOrder(order)
//            .doOnComplete { CompletableState.CompleteState }
//            .doOnError { CompletableState.ErrorState(it) }
//            .doOnSubscribe { CompletableState.LoadingState }
//            .doOnComplete { CompletableState.HidingState }
//    }


//}