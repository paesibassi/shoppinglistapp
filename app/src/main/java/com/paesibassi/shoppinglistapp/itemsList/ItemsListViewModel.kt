package com.paesibassi.shoppinglistapp.itemsList

import androidx.lifecycle.*
import com.paesibassi.shoppinglistapp.database.ItemsDao
import com.paesibassi.shoppinglistapp.database.Item
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

    fun incrementQuantityForItem(item: Item) {
        viewModelScope.launch {
            with(item.copy()) {
                this.incrementQuantity()
                dao.update(this)
            }
        }
    }

    fun decrementQuantityForItem(item: Item) {
        if (item.quantity == 0) return
        viewModelScope.launch {
            with(item.copy()) {
                this.decrementQuantity()
                if (this.quantity == 0) this.markDone()
                dao.update(this)
            }
        }
    }

    fun markItemDone(item: Item) {
        viewModelScope.launch {
            with(item.copy()) {
                this.markDone()
                dao.update(this)
            }
        }
    }
}