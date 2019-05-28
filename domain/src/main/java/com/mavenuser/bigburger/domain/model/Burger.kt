package com.mavenuser.bigburger.domain.model

data class Burger(
    var id: Long?,
    val ref: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    var price: Double,
    var count: Int = 1,
    val orderId: Long,
    val instructions: String
)