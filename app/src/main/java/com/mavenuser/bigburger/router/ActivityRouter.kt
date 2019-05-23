package com.mavenuser.bigburger.router

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.presentation.burgerDetails.BurgerDetailsFragment
import com.mavenuser.bigburger.presentation.burgerList.MainActivity
import com.mavenuser.bigburger.presentation.order.AddItemActivity
import java.lang.ref.WeakReference

class ActivityRouter(val activityRef: WeakReference<BaseActivity>) {

    enum class ROUTE{
        ORDER,
        ADD_ITEM,
        ITEM_DETAILS
    }
    fun navigate(rout:ROUTE, bundle: Bundle){
        when(rout){
            ROUTE.ADD_ITEM -> showNextScreen(AddItemActivity::class.java, bundle)
            ROUTE.ORDER -> showNextScreen(MainActivity::class.java, bundle)
            ROUTE.ITEM_DETAILS -> showNextScreen(BurgerDetailsFragment::class.java, bundle)

        }
    }
    private fun showNextScreen(clazz: Class<*> , bundle: Bundle){
        activityRef.get()?.startActivity(Intent(activityRef.get(), clazz).putExtras(bundle))
    }

    fun replaceFragment(where: Int, fragment: Fragment, tag: String) {
        activityRef.get()!!.supportFragmentManager.
            beginTransaction()
            .replace(where, fragment, tag)
            .commit()
    }
}