package com.mavenuser.bigburger.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "orders")
data class OrderEntity(@PrimaryKey(autoGenerate = true) @ColumnInfo(index = true) val id: Long?,
                       val current_order: Int)
