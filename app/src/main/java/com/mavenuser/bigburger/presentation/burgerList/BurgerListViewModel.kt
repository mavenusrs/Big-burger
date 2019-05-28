package com.mavenuser.bigburger.presentation.burgerList

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.domain.usecases.BurgerListState
import com.mavenuser.bigburger.domain.usecases.BurgerListState.DefaultState
import com.mavenuser.bigburger.domain.usecases.BurgerListUseCase
import com.mavenuser.bigburger.domain.usecases.GetOrderUseCase
import com.mavenuser.bigburger.domain.usecases.OrderState
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.mapper.mapp
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.model.OrderSerializable
import com.mavenuser.bigburger.presentation.burgerDetails.BURGER_ITEM_EXTRA
import com.mavenuser.bigburger.presentation.order.ORER_EXTRA
import com.mavenuser.bigburger.router.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class BurgerListViewModel @Inject constructor(
    private val burgerListUseCase: BurgerListUseCase,
    private val orderUseCase: GetOrderUseCase,
    private val router: Router,
    private val burgerToBurgerSerializableMapper: BurgerToBurgerSerializableMapper,
    @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
    @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler
) : ViewModel() {

    val loadingObservable = ObservableBoolean()
    val burgerListObservableList = ObservableArrayList<BurgerSerializable>()
    val errorPublishSubject = PublishSubject.create<BurgerListState.ErrorState>()

    val orderObservable = ObservableField<OrderSerializable>()


    private val compositeDisposable = CompositeDisposable()


    private fun handleResponseStates(burgerListState: BurgerListState) {

        loadingObservable.set(burgerListState == BurgerListState.LoadingState)

        when (burgerListState) {
            is DefaultState -> {
                Log.e("handleResponseStates", "DefaultState")

                burgerListObservableList.clear()
                burgerListObservableList.addAll(
                    burgerToBurgerSerializableMapper.map(burgerListState.data)
                )

            }

            is BurgerListState.ErrorState -> {
                Log.e("handleResponseStates", "ErrorState")

                errorPublishSubject.onNext(burgerListState)
            }
        }
    }


    fun bind() {
        burgerListUseCase.execute()
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe {handleResponseStates(it) }
            .addTo(compositeDisposable)
    }

    fun attached() {
        orderUseCase.execute()
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe {handleCurrentOrder(it) }
            .addTo(compositeDisposable)
    }

    private fun handleCurrentOrder(orderState: OrderState) {
        when (orderState) {

            is OrderState.DefaultState -> {
                Log.d(BurgerListViewModel::class.java.name, "orderState ${orderState.toString()}")

                orderObservable.set(orderState.data.mapp())
            }
            is OrderState.ErrorState -> {
                Log.d(BurgerListViewModel::class.java.name, "orderState ${orderState.toString()}")

            }
        }

    }

    fun unBound() {
        Log.d(BurgerListViewModel::class.java.name, "unBound")

        compositeDisposable.clear()
    }

    fun onItemClick(burgerSerializable: BurgerSerializable) {
        router.navigate(Router.ROUTE.ITEM_DETAILS, Bundle().apply {
            burgerSerializable.apply {
                 count =  count.takeUnless { it == 0 } ?: 1
            }

            this.putSerializable(BURGER_ITEM_EXTRA, burgerSerializable)
        })
    }

    fun onOrderClick(view: View) {
        router.navigate(Router.ROUTE.ORDER, Bundle().apply {
            putSerializable(ORER_EXTRA, orderObservable.get())
        })
    }

}

@JvmName("addToComposite")
private fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

