package com.mavenuser.bigburger.ext

import android.content.res.Resources
import android.databinding.BindingConversion
import android.view.View
import com.mavenuser.bigburger.R


@BindingConversion
fun setVisibilty(state: Boolean): Int{
    return if (state) View.VISIBLE else View.GONE
}


fun bindPrice(price: Double, resources: Resources): CharSequence? {
    val formatedPrice = String.format("%.2f", price/2)
    return "$formatedPrice ${resources.getString(R.string.turkeyLira)}"
}