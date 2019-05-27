package com.mavenuser.bigburger.model

import java.io.Serializable

data class OrderSerializable(var id: Long?,
                             val current_order: Int):Serializable
