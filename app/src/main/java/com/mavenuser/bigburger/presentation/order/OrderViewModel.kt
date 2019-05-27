package com.mavenuser.bigburger.presentation.order

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.lifecycle.ViewModel
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.domain.usecases.*
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.model.OrderSerializable
import com.mavenuser.bigburger.router.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by reda on 5/27/19.
 *
 */
class OrderViewModel @Inject constructor(private val burgerListOfOrderUseCase: BurgerListOfOrderUseCase,
                                         private val deleteBurgerListUseCase: BurgerListUseCase,
                                         private val deleteOrderUseCase: DeleteOrderUseCase,
                                         private val burgerMapper: BurgerToBurgerSerializableMapper,
                                         private val orderMapper: BurgerToBurgerSerializableMapper,
                                         private val router: Router,
                                         @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
                                         @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler) : ViewModel() {

    val loadingObservableBoolean  = ObservableBoolean()
    val burgerList = ObservableArrayList<BurgerSerializable>()
    val errorSubjectPublish = PublishSubject.create<BurgerListState.ErrorState>()

    lateinit var orderSerializable: OrderSerializable

    private val compositeDisposable= CompositeDisposable()

    fun bind(){
        burgerListOfOrderUseCase
            .execute(orderSerializable.id!!)
            .observeOn(observeOnScheduler)
            .subscribeOn(subscribeOnScheduler)
            .subscribe { handelGettingBurgerList(it) }
            .addTo(compositeDisposable)
    }

    private fun handelGettingBurgerList(burgerListState: BurgerListState?) {
        loadingObservableBoolean.set(burgerListState==BurgerListState.LoadingState)

        when(burgerListState){
            is BurgerListState.DefaultState -> {
                burgerList.clear()
                burgerList.addAll(burgerMapper.map(burgerListState.data))
            }
            is BurgerListState.ErrorState -> {
                errorSubjectPublish.onNext(burgerListState)
            }
        }
    }

    fun unbind() {
        compositeDisposable.clear()
    }


}