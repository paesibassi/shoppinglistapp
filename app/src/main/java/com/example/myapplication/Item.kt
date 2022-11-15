package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Item(val name: String, var quantity: Int = 1): Parcelable {
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