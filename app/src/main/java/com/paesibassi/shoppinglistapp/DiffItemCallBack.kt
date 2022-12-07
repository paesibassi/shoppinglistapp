package com.paesibassi.shoppinglistapp

import androidx.recyclerview.widget.DiffUtil

class DiffItemCallBack: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean {
        return oldItem.Id == newItem.Id
    }

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        val result = oldItem == newItem
        return result
    }
}