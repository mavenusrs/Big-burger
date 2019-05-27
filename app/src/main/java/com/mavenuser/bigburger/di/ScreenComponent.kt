package com.mavenuser.bigburger.di

import com.mavenuser.bigburger.presentation.burgerDetails.BurgerDetailsActivity
import com.mavenuser.bigburger.presentation.burgerList.BurgerListFragment
import com.mavenuser.bigburger.presentation.burgerList.MainActivity
import com.mavenuser.bigburger.presentation.order.OrderActivity
import dagger.Subcomponent

@PerScreen
@Subcomponent(modules = [ScreenModule::class])
interface ScreenComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(burgerListFragment: BurgerListFragment)

    fun inject(burgerDetailsActivity: BurgerDetailsActivity)

    fun inject(orderActivity: OrderActivity)




}