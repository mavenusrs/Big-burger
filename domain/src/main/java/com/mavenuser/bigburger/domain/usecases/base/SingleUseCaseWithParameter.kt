package com.mavenuser.bigburger.domain.usecases.base

import io.reactivex.Single

interface SingleUseCaseWithParameter<P, R> {

    fun execute(parameter: P): Single<R>

}