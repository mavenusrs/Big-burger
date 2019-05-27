package com.mavenuser.bigburger.presentation.burgerList

import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.ext.bindPrice
import com.mavenuser.bigburger.model.BurgerSerializable
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.burger_item.view.*
import java.lang.Exception

class BurgerListRecyclerAdapter(burgerList: ObservableList<BurgerSerializable>):
    BaseListAdapter<BurgerSerializable, BurgerListRecyclerAdapter.BurgerViewHolder>(burgerList) {


    lateinit var onClickListener : (burgerSerializable: BurgerSerializable) -> Unit

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BurgerViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.burger_grid_item, p0, false)
        return BurgerViewHolder(view, onClickListener)
    }


    override fun onBindViewHolder(holder: BurgerViewHolder, position: Int) {

        holder.bindItemToVIew(getItem(position))
    }




class BurgerViewHolder(itemView: View, private val onClickListener: (BurgerSerializable) -> Unit) : RecyclerView.ViewHolder(itemView) {


    fun bindItemToVIew(item: BurgerSerializable) {

        itemView.apply {
            tvPrice.text = bindPrice(item.price)
            tvTitle.text = item.title

            if (!TextUtils.isEmpty(item.thumbnail))
                Picasso.get().load(item.thumbnail).into(ivThumbnail, calbackOnError {
                    Picasso.get().load(R.drawable.bph).into(ivThumbnail)
                })

            setOnClickListener { onClickListener.invoke(item) }
        }

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


}
}