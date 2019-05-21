package com.mavenuser.bigburger.di

import dagger.Module
import dagger.Provides
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Named

const val SCHEDULAR_MAIN_THREAD = "mainThread"
const val SCHEDULAR_IO = "io"


@Module
class ApplicationModule {

    @Provides
    @Named(SCHEDULAR_MAIN_THREAD)
    fun provideAndroidMainThreadSchedular(): Scheduler = AndroidSchedulers.mainThread()

    @Provides
    @Named(SCHEDULAR_IO)
    fun provideIOThreadSchedular(): Scheduler = Schedulers.io()

}