package com.mavenuser.bigburger.presentation.burgerList

import android.arch.lifecycle.ViewModel
import android.databinding.ObservableArrayList
import android.databinding.ObservableBoolean
import android.util.Log
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.domain.usecases.BurgerListState
import com.mavenuser.bigburger.domain.usecases.BurgerListState.DefaultState
import com.mavenuser.bigburger.domain.usecases.BurgerListUseCase
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.model.BurgerSerializable
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import javax.inject.Inject
import javax.inject.Named

class BurgerListViewModel @Inject constructor(private val burgerListUseCase: BurgerListUseCase,
                                              private val burgerToBurgerSerializableMapper: BurgerToBurgerSerializableMapper,
                                              @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
                                              @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler): ViewModel() {

    val loadingObservable = ObservableBoolean()
    val burgerListObservableList = ObservableArrayList<BurgerSerializable>()

    private val compositeDisposable = CompositeDisposable()



    private fun handleResponseStates(burgerListState: BurgerListState) {
        Log.e("handleResponseStates", "fun")

        loadingObservable.set(burgerListState == BurgerListState.LoadingState)

        when (burgerListState){
            is DefaultState -> {
                Log.e("DefaultState", "addall")

                burgerListObservableList.addAll(
                    burgerToBurgerSerializableMapper.map(burgerListState.data))

            }

            is BurgerListState.ErrorState -> {
                Log.e("ErrorState", "error")
            }
        }
    }


    fun bind(){
        burgerListUseCase.getWholeBurgerList()
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
                .subscribe {
                    Log.d(BurgerListState::class.java.name, "handleResponseStates")
                    handleResponseStates(it) }
            .addTo(compositeDisposable)
    }

    fun unBound(){
        Log.e(BurgerListViewModel::class.java.name, "unBound")

        compositeDisposable.clear()
    }


}

@JvmName("addToComposite")
private fun Disposable.addTo(compositeDisposable: CompositeDisposable) {
    compositeDisposable.add(this)
}

