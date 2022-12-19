package com.paesibassi.shoppinglistapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items_table")
data class Item(
    @ColumnInfo val name: String,
    @ColumnInfo var quantity: Int = 1,
    @ColumnInfo var description: String? = null,
    @ColumnInfo var complete: Boolean = false,
    @PrimaryKey(autoGenerate = true) val Id: Long = 0L
) {
    fun incrementQuantity(): Int = this.quantity++

    fun decrementQuantity(): Int = this.quantity--

    fun markDone() {
        this.complete = !this.complete
    }
}
