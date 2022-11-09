package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var spesa: MutableList<Item>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        spesa = if (savedInstanceState != null) {
            val names = savedInstanceState.getStringArray("names") ?: arrayOf("Nothing")
            val quantities = savedInstanceState.getIntArray("quantities") ?: intArrayOf(1)
            names.mapIndexed { i, name -> Item(name, quantities[i]) }.toMutableList()
        } else {
            val initial: Array<String> = resources.getStringArray(R.array.spesa)
            initial.map { Item(it) }.toMutableList()
        }

        val totalCount = findViewById<TextView>(R.id.totalCount)
        totalCount.text = countItems(spesa)

        val itemsList = findViewById<ListView>(R.id.itemsList)
        itemsList.adapter = ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, spesa)

        itemsList.setOnItemClickListener { parent, view, position, id ->
            val element: String = parent.getItemAtPosition(position).toString()
            spesa.removeAt(position)
            itemsList.invalidateViews()
            totalCount.text = "Rimosso $element"
        }

        findViewById<Button>(R.id.addButton).setOnClickListener {
            val input = findViewById<TextView>(R.id.editTextItem)
                .text
                .toString()
                .trim()
                .replaceFirstChar { it.uppercase() }
            val newItem = Item(input)
            if (spesa.contains(newItem)) {
                spesa[spesa.indexOf(newItem)].quantity += 1
            } else {
                spesa.add(newItem)
            }
            itemsList.invalidateViews()
            totalCount.text = countItems(spesa)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putStringArray("names", spesa.map { it.name }.toTypedArray())
        outState.putIntArray("quantities", spesa.map { it.quantity }.toIntArray())
        super.onSaveInstanceState(outState)
    }
}

fun countItems(spesa: MutableList<Item>): String {
    return "Totale nella lista: ${spesa.count()}"
}

data class Item(val name: String, var quantity: Int = 1) {
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
