package com.mavenuser.bigburger.router

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.presentation.burgerDetails.BurgerDetailsActivity
import com.mavenuser.bigburger.presentation.burgerList.BURGER_LIST_FRAGMENT_TAG
import com.mavenuser.bigburger.presentation.burgerList.BurgerListFragment
import com.mavenuser.bigburger.presentation.burgerList.MainActivity
import com.mavenuser.bigburger.presentation.order.OrderActivity
import java.lang.ref.WeakReference

class Router(val activityRef: WeakReference<BaseActivity>) {

    enum class ROUTE {
        ORDER,
        ITEM_DETAILS,
        BURGER_LIST
    }

    fun navigate(rout: ROUTE, bundle: Bundle) {
        when (rout) {
            ROUTE.ORDER -> showNextScreen(OrderActivity::class.java, bundle)
            ROUTE.ITEM_DETAILS -> showNextScreen(BurgerDetailsActivity::class.java, bundle)
            ROUTE.BURGER_LIST -> showNextScreen(MainActivity::class.java, bundle)

        }
    }

    private fun showNextScreen(clazz: Class<*>, bundle: Bundle) {
        activityRef.get()?.startActivity(Intent(activityRef.get(), clazz).putExtras(bundle))
    }

    private fun replaceFragment(where: Int, fragment: Fragment, tag: String, isReplace: Boolean) {
        val fragmentManager = activityRef.get()!!.supportFragmentManager
        val fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.also { fegTans ->
            val previousFrag = activityRef.get()!!.supportFragmentManager.findFragmentById(where)
            when (isReplace) {
                true -> {
                    fegTans.replace(where, fragment, tag)
                }
                false -> {
                    fegTans.hide(previousFrag!!)
                    fegTans.replace(where, fragment, tag)
                }
            }
        }
            .addToBackStack(tag)
            .commit()
    }

    fun finishCurrentActivity() {

        activityRef.get()?.finish()
    }
}