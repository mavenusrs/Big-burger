package com.mavenuser.bigburger.model

import java.io.Serializable

data class BurgerSerializable(
    val id: Long?,
    val ref: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    var price: Double,
    var count: Int = 1,
    var orderId: Long,
    val instructions: String
) : Serializable