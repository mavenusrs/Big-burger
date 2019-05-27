package com.mavenuser.bigburger.domain.usecases.base

import io.reactivex.Observable


interface ObservableUseCase<R> {

    fun execute(): Observable<R>

}