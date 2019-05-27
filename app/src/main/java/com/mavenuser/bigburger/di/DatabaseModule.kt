package com.mavenuser.bigburger.di

import android.content.Context
import androidx.room.Room
import com.mavenuser.bigburger.data.local.OrderDAO
import com.mavenuser.bigburger.data.local.OrderingDatabase
import com.mavenuser.bigburger.data.local.dao.BurgerDAO
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DatabaseModule {

    @Singleton
    @Provides
    fun provideRoomDatabase(context: Context): OrderingDatabase{
       return Room.databaseBuilder(context, OrderingDatabase::class.java, "OrderingDB").build()
    }

    @Singleton
    @Provides
    fun provideBurgerDao(orderingDatabase: OrderingDatabase):BurgerDAO{
        return orderingDatabase.burgerDAO()
    }

    @Singleton
    @Provides
    fun provideOrderDao(orderingDatabase: OrderingDatabase):OrderDAO{
        return orderingDatabase.orderDAO()
    }
}