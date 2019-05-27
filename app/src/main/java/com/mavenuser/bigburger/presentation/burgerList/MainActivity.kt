package com.mavenuser.bigburger.presentation.burgerList

import android.os.Bundle
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.router.Router
import javax.inject.Inject

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}
