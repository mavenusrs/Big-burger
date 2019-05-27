package com.mavenuser.bigburger.presentation.order

import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.ext.bindPrice
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.presentation.burgerList.BaseListAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.burger_item.view.*
import kotlinx.android.synthetic.main.burger_item.view.tvDescription
import kotlinx.android.synthetic.main.burger_item.view.tvPrice
import kotlinx.android.synthetic.main.burger_item.view.tvTitle
import kotlinx.android.synthetic.main.order_item.view.*
import java.lang.Exception

/**
 * Created by reda on 5/27/19.
 *
 */
class OrderAdapter(
    val burgerObservableList:
    ObservableList<BurgerSerializable>) :
    BaseListAdapter<BurgerSerializable, OrderAdapter.OrderItemsViewHolder>(burgerObservableList) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder {
         val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderItemsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) {
        holder.bindItemToView(burgerObservableList[position])
    }

    class OrderItemsViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
        fun bindItemToView(burgerSerializable: BurgerSerializable) {
            view.apply {
                tvTitle.setText(burgerSerializable.title)
                tvDescription.setText(burgerSerializable.description)
                tvPrice.setText(bindPrice(burgerSerializable.price))
                tvInstruction.setText(burgerSerializable.instructions)
                numberPicker.value =  burgerSerializable.count

                if (!TextUtils.isEmpty(burgerSerializable.thumbnail))
                    Picasso.get().load(burgerSerializable.thumbnail).into(ivOThumbnail, calbackOnError {
                        Picasso.get().load(R.drawable.bph).into(ivOThumbnail)
                    })
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