package com.paesibassi.shoppinglistapp

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class Item(
    @ColumnInfo val name: String,
    @ColumnInfo var quantity: Int = 1,
    @PrimaryKey(autoGenerate = true) val Id: Long = 0L
)
