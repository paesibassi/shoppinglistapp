package com.paesibassi.shoppinglistapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paesibassi.shoppinglistapp.databinding.ItemBinding

class ItemsAdapter(
    private val itemClickListener: (item: Item) -> Unit,
    private val itemLongClickListener: (item: Item) -> Boolean
) :
    ListAdapter<Item, ItemsAdapter.ItemViewHolder>(DiffItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.dataBind(item, itemClickListener, itemLongClickListener)
    }

    class ItemViewHolder(private val binding: ItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun inflateFrom(parent: ViewGroup): ItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemBinding.inflate(layoutInflater, parent, false)
                return ItemViewHolder(binding)
            }
        }

        fun dataBind(
            item: Item,
            itemClickListener: (item: Item) -> Unit,
            itemLongClickListener: (item: Item) -> Boolean
        ) {
            binding.item = item
            binding.root.setOnClickListener { itemClickListener(item) }
            binding.root.setOnLongClickListener { itemLongClickListener(item) }
        }
    }

}
