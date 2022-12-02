package com.paesibassi.shoppinglistapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel

class ItemDetailViewModel(itemId: Long, dao: ItemsDao): ViewModel() {
    val item: LiveData<Item> = dao.getItemById(itemId)
}