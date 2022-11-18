package com.example.myapplication

import androidx.lifecycle.ViewModel

class ItemsListViewModel: ViewModel() {
    var list = mutableListOf<Item>(
        Item("Birra"), Item("Patatine"), Item("Gelato")
    )
}