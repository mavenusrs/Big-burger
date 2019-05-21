package com.mavenuser.bigburger.model

import java.io.Serializable

data class BurgerSerializable(val ref: String,
                              val title: String,
                              val description: String,
                              val thumbnail: String,
                              val price: Double):Serializable
