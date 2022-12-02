package com.paesibassi.shoppinglistapp

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ItemDetailViewModelFactory(private val itemId: Long, private val dao: ItemsDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        if (modelClass.isAssignableFrom(ItemDetailViewModel::class.java)) return ItemDetailViewModel(itemId, dao) as T
        throw IllegalArgumentException("Unknown ViewModel")
    }
}
