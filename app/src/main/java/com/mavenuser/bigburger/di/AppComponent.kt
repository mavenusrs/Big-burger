package com.mavenuser.bigburger.di

import com.mavenuser.bigburger.BurgerApplication
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class, ApiModule::class, RepositoryModule::class])
interface AppComponent
{
    fun inject (application: BurgerApplication)

    fun plus(screenModule: ScreenModule): ScreenComponent
}