package com.mavenuser.bigburger.presentation.order

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ObservableList
import androidx.recyclerview.widget.RecyclerView
import com.mavenuser.bigburger.R
import com.mavenuser.bigburger.ext.bindImageSrc
import com.mavenuser.bigburger.ext.bindPrice
import com.mavenuser.bigburger.model.BurgerSerializable
import com.mavenuser.bigburger.presentation.burgerList.BaseListAdapter
import kotlinx.android.synthetic.main.burger_item.view.tvDescription
import kotlinx.android.synthetic.main.burger_item.view.tvPrice
import kotlinx.android.synthetic.main.burger_item.view.tvTitle
import kotlinx.android.synthetic.main.order_item.view.*


/**
 * Created by reda on 5/27/19.
 *
 */
class OrderAdapter(
    val burgerObservableList:
    ObservableList<BurgerSerializable>) :
    BaseListAdapter<BurgerSerializable, OrderAdapter.OrderItemsViewHolder>(burgerObservableList) {

    lateinit var onBurgerItemClickListener : (BurgerSerializable) -> Unit
    lateinit var onDeleteItemClickListener : (Int) -> Unit

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderItemsViewHolder {
         val itemView = LayoutInflater.from(parent.context).inflate(R.layout.order_item, parent, false)
        return OrderItemsViewHolder(itemView, onBurgerItemClickListener, onDeleteItemClickListener)
    }

    override fun onBindViewHolder(holder: OrderItemsViewHolder, position: Int) {
        holder.bindItemToView(burgerObservableList[position])
    }

    class OrderItemsViewHolder(
        private val view: View,
        private val onBurgerItemClickListener: (BurgerSerializable) -> Unit,
        private val onDeleteItemClickListener: (Int) -> Unit) : RecyclerView.ViewHolder(view) {

        fun bindItemToView(burgerSerializable: BurgerSerializable) {
            view.apply {
                tvTitle.text = burgerSerializable.title
                tvDescription.text = burgerSerializable.description
                tvPrice.text = bindPrice(burgerSerializable.price)
                tvInstruction.text = burgerSerializable.instructions
                tvCount.text =  tvCount.text.replace(Regex("%"), "" + burgerSerializable.count)

                bindImageSrc(ivOThumbnail, burgerSerializable.thumbnail )


                setOnClickListener { onBurgerItemClickListener.invoke(burgerSerializable) }


                btnDelete.setOnClickListener { onDeleteItemClickListener.invoke(adapterPosition) }
            }


        }

    }


}