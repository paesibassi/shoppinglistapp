package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentItemsListBinding

class ItemsListFragment : Fragment() {
    private lateinit var list: MutableList<Item>
    private var _binding: FragmentItemsListBinding? = null // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        val view = binding.root

        list = if (savedInstanceState != null) {
            val items = savedInstanceState.getParcelableArray("items_list")?.filterIsInstance<Item>() ?: listOf<Item>()
            items.toMutableList()
        } else {
            val initial: Array<String> = resources.getStringArray(R.array.shopping_list)
            initial.map { Item(it) }.toMutableList()
        }

        binding.totalCount.text = countItems(list)

        binding.itemsList.adapter = ArrayAdapter<Item>(view.context, android.R.layout.simple_list_item_1, list)
        binding.itemsList.setOnItemClickListener { parent, _, position, _ ->
            val item: Item? = parent.getItemAtPosition(position) as? Item
            val action = ItemsListFragmentDirections.actionItemsListFragmentToItemDetailFragment(item)
            view.findNavController().navigate(action)
        }

        binding.itemsList.setOnItemLongClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            list.removeAt(position)
            binding.itemsList.invalidateViews()
            binding.totalCount.text = getString(R.string.removed_item, item.toString())
            true // returning true to consume the click event, otherwise onClick will be called next
        }

        binding.addButton.setOnClickListener {
            val input = binding.editTextItem
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
            binding.itemsList.invalidateViews()
            binding.totalCount.text = countItems(list)
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putParcelableArray("items_list", list.toTypedArray())
        super.onSaveInstanceState(outState)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun countItems(items: MutableList<Item>): String {
        return getString(R.string.list_total_count, items.count())
    }
}

