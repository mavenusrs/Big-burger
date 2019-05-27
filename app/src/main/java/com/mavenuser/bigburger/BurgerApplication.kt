package com.mavenuser.bigburger

import android.app.Application
import com.mavenuser.bigburger.di.AppComponent
import com.mavenuser.bigburger.di.ApplicationModule
import com.mavenuser.bigburger.di.DaggerAppComponent

class BurgerApplication: Application() {

    lateinit var component: AppComponent

    override fun onCreate() {
        super.onCreate()
        inject()
    }

    private fun inject() {
        component = DaggerAppComponent.builder().applicationModule(ApplicationModule(this))
            .build()
        component.inject(this)
    }

}