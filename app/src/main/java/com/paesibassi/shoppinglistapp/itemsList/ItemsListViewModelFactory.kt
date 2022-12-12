package com.paesibassi.shoppinglistapp.itemsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.paesibassi.shoppinglistapp.database.ItemsDao

class ItemsListViewModelFactory(private val dao: ItemsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ItemsListViewModel::class.java)) return ItemsListViewModel(dao) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
