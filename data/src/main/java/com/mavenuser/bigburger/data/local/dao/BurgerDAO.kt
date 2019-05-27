package com.mavenuser.bigburger.data.local.dao

import androidx.room.*
import com.mavenuser.bigburger.data.local.entity.BurgerResponse
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface BurgerDAO {

    @Insert
    fun insertBurger(burgerResponse: BurgerResponse): Completable

    @Delete
    fun deleteBurger(burgers: Array<BurgerResponse>): Completable

    @Query("Select * from burgers where order_id == :id" )
    fun getAllBurgers(id: Long) : Single<List<BurgerResponse>>


    /**
     *  update burger will not be valid, because,
     *  user could order the same order twice with different instruction
     */
    @Update
    fun updateBurger(burgerResponse: BurgerResponse): Completable
}