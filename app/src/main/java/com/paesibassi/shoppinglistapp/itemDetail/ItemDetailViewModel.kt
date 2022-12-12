package com.paesibassi.shoppinglistapp.itemDetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.paesibassi.shoppinglistapp.database.ItemsDao
import com.paesibassi.shoppinglistapp.database.Item

class ItemDetailViewModel(itemId: Long, dao: ItemsDao): ViewModel() {
    val item: LiveData<Item> = dao.getItemById(itemId)
}