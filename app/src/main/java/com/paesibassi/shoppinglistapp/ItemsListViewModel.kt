package com.paesibassi.shoppinglistapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class ItemsListViewModel : ViewModel() {
    private val _list = MutableLiveData(
        mutableListOf(Item("Birra"), Item("Patatine"), Item("Gelato"))
    )
    val list: LiveData<MutableList<Item>>
        get() = _list

    val countItems: LiveData<Int> = Transformations.map(list) { it.size }

    fun addItem(input: String, quantity: Int = 1, position: Int = 0) {
        val item = Item(input
            .trim()
            .replaceFirstChar { it.uppercase() },
            quantity)
        _list.value = _list.value?.apply {
            if (this.contains(item)) {
                this[this.indexOf(item)].quantity++
            } else {
                val pos = if (position != 0) position else this.size
                this.add(pos, item)
            }
        }
    }

    fun removeItemAt(position: Int): Item? {
        val item = _list.value?.get(position)
        _list.value = this._list.value?.apply {
            this.removeAt(position)
        }
        return item
    }
}