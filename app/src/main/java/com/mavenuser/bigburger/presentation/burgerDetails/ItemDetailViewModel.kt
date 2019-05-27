package com.mavenuser.bigburger.presentation.burgerDetails

import android.util.Log
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.domain.model.Order
import com.mavenuser.bigburger.domain.usecases.*
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.mapper.mapp
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.model.OrderSerializable
import com.mavenuser.bigburger.router.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class ItemDetailViewModel @Inject constructor(private val router: Router,
                                              private val getOrderUseCase: GetOrderUseCase,
                                              private val addOrderUseCase: AddOrderUseCase,
                                              private val addItemToOrderUseCase: AddItemToOrderUseCase,
                                              private val burgerToBurgerSerializableMapper: BurgerToBurgerSerializableMapper,
                                              @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
                                              @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler):ViewModel(){


    val loadingObservable = ObservableBoolean()
    val errorPublishSubject = PublishSubject.create<OrderState.ErrorState>()
    val itemAddedPublishSubject = PublishSubject.create<Boolean>()
    val orderSerializableObservableField = ObservableField<OrderSerializable>()

    private val compositeDisposable= CompositeDisposable()


    fun getCurrentOrder(){
        getOrderUseCase.execute()
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe {
                handleGettingCurrentOrder(it)
            }.addTo(compositeDisposable)
    }

    private fun handleGettingCurrentOrder(orderState: OrderState) {
        loadingObservable.set(orderState == OrderState.LoadingState)


        when (orderState){
            is OrderState.DefaultState -> {
                orderSerializableObservableField
                    .set(orderState.data.mapp())
                Log.d(ItemDetailViewModel::class.java.name, "id ${orderState.data.id}")

                Log.d(ItemDetailViewModel::class.java.name, "handleGettingCurrentOrder success")
            }

            is OrderState.ErrorState -> {
                Log.d(ItemDetailViewModel::class.java.name, "handleGettingCurrentOrder error")

                errorPublishSubject.onNext(orderState)
            }

        }
    }

    fun updateOrInsertOrder(burgerSerializable: BurgerSerializable){

        if (orderSerializableObservableField.get()!= null){
            addingBurgerItemToOrder(burgerSerializable)

        }else {

            addOrderUseCase.execute(Order(null, 1 ))
                .observeOn(observeOnScheduler)
                .subscribeOn(subscribeOnScheduler)
                .doOnError {
                    Log.d(ItemDetailViewModel::class.java.name, "updateOrInsertOrder error")

                    errorPublishSubject.onNext(OrderState.ErrorState(it))
                }.doOnSubscribe {
                    loadingObservable.set(true)
                }.subscribe {
                    Log.d(ItemDetailViewModel::class.java.name, "updateOrInsertOrder success")
                    loadingObservable.set(false)

                    orderSerializableObservableField.set(OrderSerializable( it, 1))

                    addingBurgerItemToOrder(burgerSerializable)
                }
                .addTo(compositeDisposable)
        }

    }


    fun addingBurgerItemToOrder(burgerSerializable: BurgerSerializable) {

        burgerSerializable.orderId = orderSerializableObservableField.get()?.id!!

        addItemToOrderUseCase.execute(burgerSerializable.
            let { burgerToBurgerSerializableMapper.inverseMap(burgerSerializable) })
            .observeOn(observeOnScheduler)
            .subscribeOn(subscribeOnScheduler)
            .doOnError {
                Log.d(ItemDetailViewModel::class.java.name, "handleAddingBurger error")

                errorPublishSubject.onNext(OrderState.ErrorState(it))}
            .doOnSubscribe {
                loadingObservable.set(true) }
            .subscribe {
                Log.d(ItemDetailViewModel::class.java.name, "handleAddingBurger success")

                handleAddingBurgerSuccessfully()
            }.addTo(compositeDisposable)

    }

    private fun handleAddingBurgerSuccessfully() {
        Log.d(ItemDetailViewModel::class.java.name, "handleAddingBurgerSuccessfully")

        itemAddedPublishSubject.onNext(true)
    }



    fun unBind(){
        compositeDisposable.clear()
    }


}