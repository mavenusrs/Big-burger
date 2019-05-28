package com.mavenuser.bigburger.presentation.order

import android.os.Bundle
import android.util.Log
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.domain.usecases.*
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.mapper.OrderToOrderSerializableMapper
import com.mavenuser.bigburger.mapper.QuotationToQuotationSerializableMapper
import com.mavenuser.bigburger.mapper.mapp
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.model.OrderSerializable
import com.mavenuser.bigburger.model.QuotationSerializable
import com.mavenuser.bigburger.presentation.burgerDetails.BURGER_ITEM_EXTRA
import com.mavenuser.bigburger.router.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by reda on 5/27/19.
 *
 */
class OrderViewModel @Inject constructor(
    private val burgerListOfOrderUseCase: BurgerListOfOrderUseCase,
    private val deleteBurgerFromOrderUseCase: DeleteItemsFromOrderUseCase,
    private val deleteOrderUseCase: DeleteOrderUseCase,
    private val burgerMapper: BurgerToBurgerSerializableMapper,
    private val orderMapper: OrderToOrderSerializableMapper,
    private val quotationMapper: QuotationToQuotationSerializableMapper,
    private val getQuoutationUseCase: GetQuoutationUseCase,
    private val router: Router,
    @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
    @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler
) : ViewModel() {

    val loadingObservableBoolean = ObservableBoolean()
    val burgerList = ObservableArrayList<BurgerSerializable>()
    val errorSubjectPublish = PublishSubject.create<BurgerListState.ErrorState>()
    val deleteSubjectPublish = PublishSubject.create<Boolean>()
    val quotationObservableField = ObservableField<QuotationSerializable>()

    lateinit var orderSerializable: OrderSerializable

    private val compositeDisposable = CompositeDisposable()

    fun bind() {
        burgerListOfOrderUseCase
            .execute(orderSerializable.id!!)
            .observeOn(observeOnScheduler)
            .subscribeOn(subscribeOnScheduler)
            .subscribe { handelGettingBurgerList(it) }
            .addTo(compositeDisposable)
    }

    private fun handelGettingBurgerList(burgerListState: BurgerListState?) {
        loadingObservableBoolean.set(burgerListState == BurgerListState.LoadingState)

        when (burgerListState) {
            is BurgerListState.DefaultState -> {
                burgerList.clear()
                burgerList.addAll(burgerMapper.map(burgerListState.data))
                getQuotation()
            }
            is BurgerListState.ErrorState -> {
                errorSubjectPublish.onNext(burgerListState)
            }
        }
    }

    fun unbind() {
        compositeDisposable.clear()
    }

    fun deleteCurrentOrder() {
        deleteOrderUseCase.execute(orderMapper.reverseMap(orderSerializable))
            .doOnError { errorSubjectPublish.onError(Throwable(router.activityRef.get()?.resources?.getString(R.string.delete_order_error))) }
            .doOnComplete {
                deleteSubjectPublish.onNext(true)
            }
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .subscribe()
            .addTo(compositeDisposable)
    }

    fun onBurgerItemClick(it: BurgerSerializable) {
        router.navigate(Router.ROUTE.ITEM_DETAILS, Bundle().apply { putSerializable(BURGER_ITEM_EXTRA, it) })
    }

    fun deleteItem(position: Int) {
        deleteBurgerFromOrderUseCase.execute(arrayOf(burgerMapper.inverseMap(burgerList[position])))
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .doOnComplete {
                loadingObservableBoolean.set(false)

                burgerList.removeAt(position)
                if (burgerList.isEmpty()){
                    deleteCurrentOrder()
                }

                getQuotation()
            }
            .doOnSubscribe {  loadingObservableBoolean.set(true) }
            .doOnError {
                loadingObservableBoolean.set(false)

                errorSubjectPublish.onNext(BurgerListState.ErrorState(it))
            }
            .subscribe()
            .addTo(compositeDisposable)

    }


    fun getQuotation(){
        getQuoutationUseCase.execute(burgerMapper.inverseMapList(burgerList))
            .observeOn(observeOnScheduler)
            .subscribeOn(Schedulers.computation())
            .doOnNext {
                it?.apply {
                    quotationObservableField.set(it.mapp())
                }
            }.doOnError {
                Log.e("getQuotation", "On Error useless here till now")

            }.subscribe().addTo(compositeDisposable)
    }


}