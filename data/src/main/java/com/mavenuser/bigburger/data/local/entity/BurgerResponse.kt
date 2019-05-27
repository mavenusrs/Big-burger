package com.mavenuser.bigburger.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose


@Entity(tableName = "burgers",
    foreignKeys = arrayOf(ForeignKey(entity = OrderEntity::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("order_id"),
    onDelete = ForeignKey.CASCADE,
    onUpdate = ForeignKey.CASCADE)
    ))
data class BurgerResponse (
    @PrimaryKey(autoGenerate = true) var id: Long?=null,
    @Expose val ref: String,
    @Expose val title: String,
    @Expose val description: String,
    @Expose val thumbnail: String,
    @Expose val price: Double,
    val count: Int,
    @ColumnInfo(index = true) val order_id: Long,
    var instructions: String?)