package com.mavenuser.bigburger.domain.usecases.base

import io.reactivex.Completable

interface CompletableUseCaseWithParameter<P> {

    fun execute(parameter: P): Completable

}