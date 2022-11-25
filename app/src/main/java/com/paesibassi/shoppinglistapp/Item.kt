package com.paesibassi.shoppinglistapp

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "items_table")
@Parcelize
data class Item(
    @ColumnInfo val name: String,
    @ColumnInfo var quantity: Int = 1,
    @PrimaryKey(autoGenerate = true) val Id: Long = 0L
) : Parcelable {
    override fun toString(): String {
        return "$name x $quantity"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Item) return false
        if (name == other.name) return true
        return false
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + quantity
        return result
    }
}