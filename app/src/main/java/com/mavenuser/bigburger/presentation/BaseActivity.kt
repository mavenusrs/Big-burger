package com.mavenuser.bigburger.presentation

import android.support.v7.app.AppCompatActivity
import com.mavenuser.bigburger.BurgerApplication
import com.mavenuser.bigburger.di.AppComponent
import com.mavenuser.bigburger.di.ScreenModule

open class BaseActivity : AppCompatActivity()  {

    val screenComponent by lazy {
        getApplicationComponet().plus(ScreenModule(this))
    }

    private fun getApplicationComponet(): AppComponent {
        return (application as BurgerApplication).component
    }
}