package com.mavenuser.bigburger.router

import android.content.Intent
import android.os.Bundle
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.presentation.burgerList.MainActivity
import java.lang.ref.WeakReference

class FragmentRouter(val activityRef: WeakReference<BaseActivity>) {

    enum class ROUTE{
        ORDER,
        ADD_ITEM
    }
    fun navigage(rout:ROUTE, bundle: Bundle){
        when(rout){
            ROUTE.ADD_ITEM -> showNextScreen(MainActivity::class.java, bundle)
        }
    }
    private fun showNextScreen(clazz: Class<*> , bundle: Bundle){
        activityRef.get()?.startActivity(Intent(activityRef.get(), clazz).putExtras(bundle))
    }
}