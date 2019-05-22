package com.mavenuser.bigburger.presentation.burgerList

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.model.BurgerSerializable
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.burger_item.view.*
import java.lang.Exception

class BurgerListRecyclerAdapter(burgerList: ObservableList<BurgerSerializable>):
    BaseListAdapter<BurgerSerializable, BurgerViewHolder>(burgerList) {


    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): BurgerViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.burger_item, p0, false)
        return BurgerViewHolder(view)
    }


    override fun onBindViewHolder(holder: BurgerViewHolder, position: Int) {

        holder.bindItemToVIew(getItem(position))
    }


}

class BurgerViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

    val resources by lazy {
        itemView.context.resources
    }

    fun bindItemToVIew(item: BurgerSerializable){

        itemView.apply {
            tvPrice.text = bindPrice(item.price)
            tvDescription.text = item.description
            tvTitle.text = item.title

            if(!TextUtils.isEmpty(item.thumbnail))
                Picasso.get().load(item.thumbnail).into(ivThumbnail, calb {
                    Picasso.get().load(R.drawable.bph).into(ivThumbnail) })

        }

}

    fun calb(f: () -> Unit):Callback = object: Callback{
        override fun onError(e: Exception?) {
            f()
        }

        override fun onSuccess() {
            // do nothing
        }
    }

    private fun bindPrice(price: Double): CharSequence? {
        return "$price ${resources.getString(R.string.turkeyLira)}"
    }
}