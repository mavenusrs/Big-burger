package com.mavenuser.bigburger.data.local

import androidx.room.*
import com.mavenuser.bigburger.data.local.entity.OrderEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface OrderDAO {

    @Query("Select * from orders where  current_order == 1" )
    fun getCurrentOrder() : Single<OrderEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(orderEntity: OrderEntity): Single<Long>

    @Delete
    fun deleteOrder(orderEntity: OrderEntity): Completable

//    @Update
//    fun updateOrder(orderEntity: OrderEntity): Completable
}
