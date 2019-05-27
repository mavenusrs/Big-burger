package com.mavenuser.bigburger.di

import com.mavenuser.bigburger.presentation.BaseActivity
import com.mavenuser.bigburger.router.Router
import dagger.Module
import dagger.Provides
import java.lang.ref.WeakReference

@Module
class ScreenModule(private val activity: BaseActivity) {

    @Provides
    @PerScreen
    fun provideActivity(): BaseActivity {
        return activity
    }

    @Provides
    @PerScreen
    fun provideActivityRouter(): Router{
        return Router(WeakReference(activity))
    }


}