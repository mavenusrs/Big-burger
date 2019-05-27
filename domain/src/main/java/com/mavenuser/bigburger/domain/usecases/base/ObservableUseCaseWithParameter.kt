package com.mavenuser.bigburger.domain.usecases.base

import io.reactivex.Observable


interface ObservableUseCaseWithParameter<P, R> {

    fun execute(parameter: P): Observable<R>

}