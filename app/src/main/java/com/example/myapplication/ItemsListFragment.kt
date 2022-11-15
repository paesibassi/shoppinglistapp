package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.navigation.findNavController

class ItemsListFragment : Fragment() {
    private lateinit var list: MutableList<Item>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_items_list, container, false)

        list = if (savedInstanceState != null) {
            val items = savedInstanceState.getParcelableArray("items_list")?.filterIsInstance<Item>() ?: listOf<Item>()
            items.toMutableList()
        } else {
            val initial: Array<String> = resources.getStringArray(R.array.shopping_list)
            initial.map { Item(it) }.toMutableList()
        }

        val totalCount = view.findViewById<TextView>(R.id.totalCount)
        totalCount.text = countItems(list)

        val itemsList = view.findViewById<ListView>(R.id.itemsList)
        itemsList.adapter = ArrayAdapter<Item>(view.context, android.R.layout.simple_list_item_1, list)
        itemsList.setOnItemClickListener { parent, _, position, _ ->
            val item: Item? = parent.getItemAtPosition(position) as? Item
            val action = ItemsListFragmentDirections.actionItemsListFragmentToItemDetailFragment(item)
            view.findNavController().navigate(action)
        }

        itemsList.setOnItemLongClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            list.removeAt(position)
            itemsList.invalidateViews()
            totalCount.text = getString(R.string.removed_item, item.toString())
            true
        }

        view.findViewById<Button>(R.id.addButton).setOnClickListener {
            val input = view.findViewById<TextView>(R.id.editTextItem)
                .text
                .toString()
                .trim()
                .replaceFirstChar { it.uppercase() }
            val newItem = Item(input)
            if (list.contains(newItem)) {
                list[list.indexOf(newItem)].quantity += 1
            } else {
                list.add(newItem)
            }
            itemsList.invalidateViews()
            totalCount.text = countItems(list)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray("items_list", list.toTypedArray())
        super.onSaveInstanceState(outState)
    }

    private fun countItems(items: MutableList<Item>): String {
        return getString(R.string.list_total_count, items.count())
    }
}

