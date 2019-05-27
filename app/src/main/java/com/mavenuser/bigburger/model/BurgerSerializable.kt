package com.mavenuser.bigburger.model

import androidx.databinding.Bindable
import com.travijuu.numberpicker.library.Interface.ValueChangedListener
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
    var instructions: String
) : Serializable {

    var watcher = ValueChangedListener { value, action ->
        count = value
    }

}