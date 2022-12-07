package com.paesibassi.shoppinglistapp

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class ItemsListViewModel(private val dao: ItemsDao) : ViewModel() {

    val items: LiveData<List<Item>> = dao.getAll()
    val countItems: LiveData<Int> = Transformations.map(items) { it.size }

    fun addItem(input: String, quantity: Int = 1) {
        if (input == "") return
        val itemName = input.trim().replaceFirstChar { it.uppercase() }
        if (items.value?.map { it.name }?.contains(itemName) == true) { // checks if the item name exists already
            val currentItem: Item? = items.value?.first { it.name == itemName }
            incrementQuantityForItem(currentItem!!) // we know item is not null because we already found it
        } else {
            val newItem = Item(itemName, quantity)
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
            with(item.copy()) {
                this.incrementQuantity()
                dao.update(this)
            }
        }
    }

    fun decrementQuantityForItem(item: Item) {
        viewModelScope.launch {
            with(item.copy()) {
                this.decrementQuantity()
                dao.update(this)
            }
        }
    }
}