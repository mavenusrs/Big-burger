package com.mavenuser.bigburger.domain.model

data class Burger(
    val ref: String,
    val title: String,
    val description: String,
    val thumbnail: String,
    var price: Double
)