package com.mavenuser.bigburger.domain.usecases.base

import io.reactivex.Single

interface SingleUseCase<R> {

    fun execute(): Single<R>

}