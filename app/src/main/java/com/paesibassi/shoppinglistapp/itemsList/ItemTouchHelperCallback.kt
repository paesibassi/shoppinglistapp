package com.paesibassi.shoppinglistapp.itemsList

import android.content.Context
import android.graphics.*
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.paesibassi.shoppinglistapp.R

abstract class ItemTouchHelperCallback(context: Context): ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

    private val clearPaint = Paint().apply {
        xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    }
    private val deleteDrawable = ContextCompat.getDrawable(context,
        R.drawable.ic_baseline_delete_outline_24
    )

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean { // implement this if you want to enable drag to reorder functionality
        return false
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
        val itemView = viewHolder.itemView
        if (!isCurrentlyActive) {
            clearCanvas(c, itemView, dX)
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, false)
            return
        }

        drawBackground(itemView, dX, c)
        drawDeleteIcon(deleteDrawable, itemView, c)

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, true)
    }

    private fun clearCanvas(c: Canvas, itemView: View, dX: Float) {
        c.drawRect(
            /* left = */ itemView.right.toFloat() + dX,
            /* top = */ itemView.top.toFloat(),
            /* right = */ itemView.right.toFloat(),
            /* bottom = */ itemView.bottom.toFloat(),
            /* paint = */ clearPaint
        )
    }

    private fun drawBackground(itemView: View, dX: Float, c: Canvas) {
        val background = ColorDrawable().apply {
            color = Color.parseColor("#b80f0a")
        }
        background.setBounds(
            /* left = */ itemView.left,
            /* top = */ itemView.top,
            /* right = */ itemView.right + dX.toInt(),
            /* bottom = */ itemView.bottom,
        )
        background.draw(c)
    }

    private fun drawDeleteIcon(deleteDrawable: Drawable?, itemView: View, c: Canvas) {
        deleteDrawable?.let {
            val intrinsicWidth = it.intrinsicWidth
            val intrinsicHeight = it.intrinsicHeight
            val iconMargin = (itemView.height - intrinsicHeight) / 2

            val iconTop = itemView.top + (itemView.height - intrinsicHeight) / 2
            val iconLeft = itemView.left + iconMargin
            val iconRight = itemView.left + iconMargin + intrinsicWidth
            val iconBottom = iconTop + intrinsicHeight

            it.setBounds(iconLeft, iconTop, iconRight, iconBottom)
            it.draw(c)
        }
    }

    override fun getSwipeThreshold(viewHolder: RecyclerView.ViewHolder): Float {
        return 0.6f
    }
}