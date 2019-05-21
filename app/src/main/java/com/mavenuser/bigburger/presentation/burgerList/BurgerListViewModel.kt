package com.mavenuser.bigburger.presentation.burgerList

import android.annotation.SuppressLint
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import com.mavenuser.bigburger.di.SCHEDULAR_MAIN_THREAD
import com.mavenuser.bigburger.di.SCHEDULAR_IO
import com.mavenuser.bigburger.presentation.burgerList.BurgerListState.DefaultState
import com.mavenuser.bigburger.domain.usecases.BurgerListUseCase
import com.mavenuser.bigburger.mapper.BurgerToBurgerSerializableMapper
import com.mavenuser.bigburger.model.BurgerSerializable
import io.reactivex.Scheduler
import javax.inject.Inject
import javax.inject.Named

class BurgerListViewModel @Inject constructor(private val burgerListUseCase: BurgerListUseCase,
                                              private val burgerToBurgerSerializableMapper: BurgerToBurgerSerializableMapper,
                                              @Named(SCHEDULAR_IO) val subscribeOnScheduler: Scheduler,
                                              @Named(SCHEDULAR_MAIN_THREAD) val observeOnScheduler: Scheduler): ViewModel() {

    val stateLiveData = MutableLiveData<BurgerListState>()

    init {
        stateLiveData.value = DefaultState(emptyList())
    }


    @SuppressLint("CheckResult")
    private fun getBurgerList(){
        burgerListUseCase.getWholeBurgerList()
            .subscribeOn(subscribeOnScheduler)
            .observeOn(observeOnScheduler)
            .map { listOfBurger ->
                burgerToBurgerSerializableMapper.map(listOfBurger) }
            .subscribe({burgerList ->
                onBurgerListRetrieved( burgerList)
                    },
                {
                        throwable: Throwable ->  onError(throwable)
                })
    }

    private fun onBurgerListRetrieved(burgerList: List<BurgerSerializable>){
        stateLiveData.value = DefaultState(burgerList)
    }

    private fun onError(throwable: Throwable){
        stateLiveData.value = BurgerListState.ErrorState(throwable)
    }

    fun resetBurgerList(){
        stateLiveData.value = BurgerListState.LoadingState
        loadList()
    }

    fun loadList(){
        getBurgerList()
    }

    fun restoreList(){

        (stateLiveData.value as DefaultState).data = obtainCurrentData()
    }

    private fun obtainCurrentData() = (stateLiveData.value as DefaultState )?.data ?: emptyList()
}

