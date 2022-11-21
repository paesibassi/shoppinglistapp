package com.paesibassi.shoppinglistapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ItemsListViewModel : ViewModel() {
    private val _list = MutableLiveData(
        mutableListOf(
            Item("Birra"), Item("Patatine"), Item("Gelato")
        )
    )
    val list: LiveData<MutableList<Item>>
        get() = _list

    private val _countItems = MutableLiveData<Int>()
    val countItems: LiveData<Int>
        get() = _countItems

    init {
        _countItems.value = this._list.value?.count()
    }

    fun addItem(input: String, quantity: Int = 1, position: Int = 0) {
        val item = Item(input
            .trim()
            .replaceFirstChar { it.uppercase() },
            quantity)
        this._list.value?.let {
            if (it.contains(item)) {
                it[it.indexOf(item)].quantity++
            } else {
                val pos = if (position != 0) position else it.count()
                it.add(pos, item)
                _countItems.value = it.count()
            }
        }
    }

    fun removeItemAt(position: Int): Item? {
        val item = this._list.value?.let {
            val item = it[position]
            it.removeAt(position)
            _countItems.value = it.count()
            item
        }
        return item
    }
}