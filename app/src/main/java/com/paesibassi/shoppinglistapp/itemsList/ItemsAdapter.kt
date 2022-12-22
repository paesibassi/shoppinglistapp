package com.paesibassi.shoppinglistapp.itemsList

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.paesibassi.shoppinglistapp.database.Item
import com.paesibassi.shoppinglistapp.databinding.ItemBinding

class ItemsAdapter(
    private val itemClickListener: (item: Item) -> Unit,
    private val itemLongClickListener: (item: Item) -> Boolean,
    private val itemPlusButtonClickListener: (item: Item) -> Unit,
    private val itemMinusButtonClickListener: (item: Item) -> Unit,
) :
    ListAdapter<Item, ItemsAdapter.ItemViewHolder>(DiffItemCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder =
        ItemViewHolder.inflateFrom(parent)

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = getItem(position)
        holder.dataBind(
            item,
            itemClickListener,
            itemLongClickListener,
            itemPlusButtonClickListener,
            itemMinusButtonClickListener,
        )
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
            itemLongClickListener: (item: Item) -> Boolean,
            itemPlusButtonClickListener: (item: Item) -> Unit,
            itemMinusButtonClickListener: (item: Item) -> Unit,
        ) {
            binding.item = item
            binding.root.setOnClickListener { itemClickListener(item) }
            binding.root.setOnLongClickListener { itemLongClickListener(item) }
            binding.plusButton.setOnClickListener { itemPlusButtonClickListener(item) }
            binding.minusButton.setOnClickListener { itemMinusButtonClickListener(item) }

            if (item.complete) {
                binding.itemName.paintFlags = (binding.itemName.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
                binding.itemQuantity.paintFlags = (binding.itemQuantity.paintFlags or Paint.STRIKE_THRU_TEXT_FLAG)
            } else {
                binding.itemName.paintFlags = (binding.itemName.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
                binding.itemQuantity.paintFlags = (binding.itemQuantity.paintFlags and Paint.STRIKE_THRU_TEXT_FLAG.inv())
            }
        }
    }
}
