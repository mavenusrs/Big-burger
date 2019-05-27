package com.mavenuser.bigburger.domain.usecases.base

import io.reactivex.Completable

interface CompletableUseCase {

    fun execute(): Completable

}