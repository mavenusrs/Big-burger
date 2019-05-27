package com.mavenuser.bigburger.ext

import android.text.TextUtils
import android.util.Log
import androidx.databinding.BindingConversion
import android.view.View
import android.widget.ImageView
import android.widget.NumberPicker
import androidx.databinding.BindingAdapter
import com.mavenuser.bigburger.R
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
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

@BindingAdapter("app:value")
fun bingClickNumberPickerView(view: NumberPicker, value: Int){
    view.value =value
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
