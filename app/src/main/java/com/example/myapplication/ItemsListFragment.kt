package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.myapplication.databinding.FragmentItemsListBinding

class ItemsListFragment : Fragment() {
    private var _binding: FragmentItemsListBinding? = null // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this).get(ItemsListViewModel::class.java)

        binding.totalCount.text = countItems(viewModel.list)

        binding.itemsList.adapter = ArrayAdapter<Item>(view.context, android.R.layout.simple_list_item_1, viewModel.list)
        binding.itemsList.setOnItemClickListener { parent, _, position, _ ->
            val item: Item? = parent.getItemAtPosition(position) as? Item
            val action = ItemsListFragmentDirections.actionItemsListFragmentToItemDetailFragment(item)
            view.findNavController().navigate(action)
        }

        binding.itemsList.setOnItemLongClickListener { parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            viewModel.list.removeAt(position)
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
            if (viewModel.list.contains(newItem)) {
                viewModel.list[viewModel.list.indexOf(newItem)].quantity += 1
            } else {
                viewModel.list.add(newItem)
            }
            binding.itemsList.invalidateViews()
            binding.totalCount.text = countItems(viewModel.list)
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun countItems(items: MutableList<Item>): String {
        return getString(R.string.list_total_count, items.count())
    }
}
