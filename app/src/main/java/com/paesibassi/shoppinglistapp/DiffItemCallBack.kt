package com.paesibassi.shoppinglistapp

import androidx.recyclerview.widget.DiffUtil

class DiffItemCallBack: DiffUtil.ItemCallback<Item>() {
    override fun areItemsTheSame(oldItem: Item, newItem: Item): Boolean = (oldItem.Id == newItem.Id)

    override fun areContentsTheSame(oldItem: Item, newItem: Item): Boolean {
        return (oldItem.name == newItem.name) && (oldItem.quantity == newItem.quantity)
    }
}