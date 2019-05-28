package com.mavenuser.bigburger.ext

import android.text.TextUtils
import android.util.Log
import androidx.databinding.BindingConversion
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.domain.usecases.OrderState
import com.mavenuser.bigburger.model.OrderSerializable
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.travijuu.numberpicker.library.Interface.ValueChangedListener
import com.travijuu.numberpicker.library.NumberPicker
import java.lang.Exception


@BindingConversion
fun setVisibilty(state: Boolean): Int{
    return if (state) View.VISIBLE else View.GONE
}

@BindingConversion
fun bindPrice(price: Double): CharSequence? {
    val formatedPrice = String.format("%.2f", price/100)
    return "$formatedPrice ₺"
}


@BindingAdapter("app:visib")
fun bingCurrentOrderBanner(view: TextView, value: ObservableField<OrderSerializable>){
    if (value?.get() == null){
         view.visibility = View.GONE

    }else view.visibility = View.VISIBLE

}

@BindingAdapter("app:ValueChangedListener")
fun bindEvent(numberPicker: NumberPicker, valueChangedListener: ValueChangedListener){

    numberPicker.valueChangedListener = valueChangedListener
}

@BindingAdapter("android:src")
fun bindImageSrc(imageView: ImageView, thumbnail: String){
    if (!TextUtils.isEmpty(thumbnail))
        Picasso.get().load(thumbnail).into(imageView, calbackOnError {
            Picasso.get().load(R.drawable.bph).into(imageView)
        })

}

    private fun calbackOnError(f: () -> Unit): Callback = object : Callback {
        override fun onError(e: Exception?) {
            f()
        }

        override fun onSuccess() {
            // do nothing bacause the image is loaded
            Log.d("Picasso", "The image is loaded")
        }
    }
