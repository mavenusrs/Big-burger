package com.mavenuser.bigburger.presentation.order

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.core.content.ContextCompat


abstract class SwipeTodeleteCallback(
    var context: Context
) : ItemTouchHelper.Callback() {
    private  var icon: Drawable?
    private var background: ColorDrawable? = null

    init {
        icon = ContextCompat.getDrawable(
            context,
            android.R.drawable.ic_menu_delete
        )
        background = ColorDrawable(Color.RED)
    }

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return makeMovementFlags(0, ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT )
    }


    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.75f
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView = viewHolder.itemView
        val backgroundCornerOffset = 20 //so background is behind the rounded corners of itemView

        val iconMargin = (itemView.height - icon?.getIntrinsicHeight()!!) / 2
        val iconTop = itemView.top + (itemView.height - icon?.getIntrinsicHeight()!!) / 2
        val iconBottom = iconTop + icon?.getIntrinsicHeight()!!

        if (dX > 0) { // Swiping to the right
            val iconLeft = itemView.left + iconMargin + icon?.getIntrinsicWidth()!!
            val iconRight = itemView.left + iconMargin
            icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background?.setBounds(
                itemView.left, itemView.top,
                itemView.left + dX.toInt() + backgroundCornerOffset, itemView.bottom
            )
        } else if (dX < 0) { // Swiping to the left
            val iconLeft = itemView.right - iconMargin - icon?.getIntrinsicWidth()!!
            val iconRight = itemView.right - iconMargin
            icon?.setBounds(iconLeft, iconTop, iconRight, iconBottom)

            background?.setBounds(
                itemView.right + dX.toInt() - backgroundCornerOffset,
                itemView.top, itemView.right, itemView.bottom
            )
        } else { // view is unSwiped
            background?.setBounds(0, 0, 0, 0)
        }

        background?.draw(c)
        icon?.draw(c)
    }


}