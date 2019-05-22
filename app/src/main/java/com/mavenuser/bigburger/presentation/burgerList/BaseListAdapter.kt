package com.mavenuser.bigburger.presentation.burgerList

import android.databinding.ObservableList
import android.support.v7.widget.RecyclerView

abstract class BaseListAdapter<D, Holder: RecyclerView.ViewHolder>
    (private val items: ObservableList<D>) : RecyclerView.Adapter<BurgerViewHolder>() {

    lateinit var onItemClickList: (item: Any) -> Unit

    init {
        items.addOnListChangedCallback(object : ObservableList.OnListChangedCallback<ObservableList<D>>(){
            override fun onChanged(sender: ObservableList<D>?) {
                notifyDataSetChanged()
            }

            override fun onItemRangeRemoved(sender: ObservableList<D>?, positionStart: Int, itemCount: Int) {

                notifyItemRangeRemoved(positionStart, itemCount)
            }

            override fun onItemRangeMoved(
                sender: ObservableList<D>?,
                fromPosition: Int,
                toPosition: Int,
                itemCount: Int
            ) {
                notifyItemMoved(fromPosition,  toPosition)
            }

            override fun onItemRangeInserted(sender: ObservableList<D>?, positionStart: Int, itemCount: Int) {

                notifyItemRangeInserted(positionStart, itemCount)
            }

            override fun onItemRangeChanged(sender: ObservableList<D>?, positionStart: Int, itemCount: Int) {

                notifyItemRangeChanged(positionStart, itemCount)
            }

        })
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun getItem(index: Int): D{
        return items[index]
    }

    fun getItems(): ObservableList<D>{
        return items
    }

}