package com.mavenuser.bigburger.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.mavenuser.bigburger.data.local.dao.BurgerDAO
import com.mavenuser.bigburger.data.local.entity.BurgerResponse
import com.mavenuser.bigburger.data.local.entity.OrderEntity

@Database(entities = [OrderEntity::class, BurgerResponse::class], version = 1, exportSchema = false)
abstract class OrderingDatabase : RoomDatabase(){


    abstract fun orderDAO():OrderDAO

    abstract fun burgerDAO():BurgerDAO

}