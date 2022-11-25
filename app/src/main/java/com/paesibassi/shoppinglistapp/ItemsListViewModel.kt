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
        if (items.value?.contains(newItem) == true) { // checks by name
            viewModelScope.launch {
                val item = dao.getItemByName(newItem.name)
                item.quantity = item.quantity.inc()
                dao.update(item)
            }
        } else {
            viewModelScope.launch { dao.insert(newItem) }
        }
    }

    fun removeItemAt(position: Int): Item? {
        val item = items.value?.get(position)
        viewModelScope.launch {
            item?.let { dao.delete(it) }
        }
        return item
    }
}