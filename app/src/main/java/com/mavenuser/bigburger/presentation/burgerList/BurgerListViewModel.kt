package com.mavenuser.bigburger.presentation.burgerList

import androidx.lifecycle.ViewModel
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import android.os.Bundle
import android.util.Log
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.domain.usecases.BurgerListState
import com.mavenuser.bigburger.domain.usecases.BurgerListState.DefaultState
import com.mavenuser.bigburger.domain.usecases.BurgerListUseCase
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.presentation.burgerDetails.BURGER_ITEM_EXTRA
import com.mavenuser.bigburger.router.Router
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject
import javax.inject.Inject
import javax.inject.Named

class BurgerListViewModel @Inject constructor(private val burgerListUseCase: BurgerListUseCase,
                                              private val router: Router,
                                              private val burgerToBurgerSerializableMapper: BurgerToBurgerSerializableMapper,
                                              @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
                                              @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler): ViewModel() {

    val loadingObservable = ObservableBoolean()
    val burgerListObservableList = ObservableArrayList<BurgerSerializable>()
    val errorPublishSubject = PublishSubject.create<BurgerListState.ErrorState>()

    private val compositeDisposable = CompositeDisposable()


    private fun handleResponseStates(burgerListState: BurgerListState) {

        loadingObservable.set(burgerListState == BurgerListState.LoadingState)

        when (burgerListState){
            is DefaultState -> {
                Log.e("handleResponseStates", "DefaultState")

                burgerListObservableList.clear()
                burgerListObservableList.addAll(
                    burgerToBurgerSerializableMapper.map(burgerListState.data))

            }

            is BurgerListState.ErrorState -> {
                Log.e("handleResponseStates", "ErrorState")

                errorPublishSubject.onNext(burgerListState)
            }
        }
    }


    fun bind(){
        burgerListUseCase.execute()
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
                .subscribe {
                    Log.d(BurgerListState::class.java.name, "handleResponseStates")
                    handleResponseStates(it) }
            .addTo(compositeDisposable)
    }

    fun unBound(){
        Log.d(BurgerListViewModel::class.java.name, "unBound")

        compositeDisposable.clear()
    }

    fun onItemClick(burgerSerializable: BurgerSerializable) {
        router.navigate(Router.ROUTE.ITEM_DETAILS, Bundle().apply {
           this.putSerializable(BURGER_ITEM_EXTRA, burgerSerializable)
        })
    }


}

@JvmName("addToComposite")
private fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

