package com.paesibassi.shoppinglistapp

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ItemsListViewModel(private val dao: ItemsDao) : ViewModel() {

    val items: LiveData<List<Item>> = dao.getAll()
    val countItems: LiveData<Int> = Transformations.map(items) { it.size }

    fun addItem(input: String, quantity: Int = 1) {
        val newItem = Item(
            input.trim().replaceFirstChar { it.uppercase() },
            quantity
        )
        val itemNames = items.value?.map { it.name }?.toList()
        if (itemNames?.contains(newItem.name) == true) { // checks by name
            viewModelScope.launch {
                val item = dao.getItemByName(newItem.name)
                incrementQuantityForItem(item)
            }
        } else {
            viewModelScope.launch { dao.insert(newItem) }
        }
    }

    fun removeItem(item: Item) {
        viewModelScope.launch { dao.delete(item) }
    }

    fun removeItemAt(position: Int): Item? {
        val item = items.value?.get(position)
        viewModelScope.launch {
            item?.let { dao.delete(it) }
        }
        return item
    }

    fun incrementQuantityForItem(item: Item) {
        viewModelScope.launch {
            item.quantity = item.quantity.inc()
            dao.update(item)
        }
    }

    fun decrementQuantityForItem(item: Item) {
        viewModelScope.launch {
            item.quantity = item.quantity.dec()
            dao.update(item)
        }
    }
}