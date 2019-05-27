package com.mavenuser.bigburger.domain.usecases


sealed class CompletableState {

    object CompleteState: CompletableState()

    data class ErrorState(val throwable: Throwable): CompletableState()

    object LoadingState: CompletableState()

    object HidingState: CompletableState()


}