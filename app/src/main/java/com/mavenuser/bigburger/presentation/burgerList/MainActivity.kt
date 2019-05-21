package com.mavenuser.bigburger.presentation.burgerList

import android.os.Bundle
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.router.ActivityRouter
import javax.inject.Inject

class MainActivity : BaseActivity() {

    @Inject lateinit var activityRouter: ActivityRouter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        screenComponent.inject(this)

        if(savedInstanceState == null)
            activityRouter.replaceFragment(R.id.container, BurgerListFragment.newInstance()
            ,BURGER_LIST_FRAGMENT_TAG)
    }
}
