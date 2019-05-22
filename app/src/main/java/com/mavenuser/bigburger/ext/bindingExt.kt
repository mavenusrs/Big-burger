package com.mavenuser.bigburger.ext

import android.databinding.BindingConversion
import android.view.View


@BindingConversion
fun setVisibilty(state: Boolean): Int{
    return if (state) View.VISIBLE else View.GONE
}