package com.paesibassi.shoppinglistapp

import android.util.Log
import androidx.recyclerview.widget.DiffUtil

class DiffItemCallBack: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        val result = oldItem == newItem
        Log.i("result", "oldItem: $oldItem, newItem: $newItem, same: $result")
        // FIXME sometimes the object gets updated before this check takes place,
        //  and then the UI is not updated
        return result
    }
}