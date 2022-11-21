package com.paesibassi.shoppinglistapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.paesibassi.shoppinglistapp.databinding.FragmentItemsListBinding
import com.google.android.material.snackbar.Snackbar

class ItemsListFragment : Fragment() {
    private var _binding: FragmentItemsListBinding? = null // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemsListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemsListBinding.inflate(inflater, container, false)
        val view = binding.root
        viewModel = ViewModelProvider(this)[ItemsListViewModel::class.java]
        binding.itemsListViewModel = viewModel // data binding
        binding.lifecycleOwner = viewLifecycleOwner // live data for data binding


        viewModel.list.value?.let {
            val adapter = ArrayAdapter(view.context, android.R.layout.simple_list_item_1, it)
            adapter.count
            binding.itemsList.adapter = adapter
        }
        binding.itemsList.setOnItemClickListener { parent, _, position, _ ->
            val item: Item? = parent.getItemAtPosition(position) as? Item
            val action =
                ItemsListFragmentDirections.actionItemsListFragmentToItemDetailFragment(item)
            view.findNavController().navigate(action)
        }

        binding.itemsList.setOnItemLongClickListener { parent, _, position, _ ->
            val item = viewModel.removeItemAt(position)
            binding.itemsList.invalidateViews()
            if (item != null) {
                Snackbar.make(
                    parent,
                    getString(R.string.removed_item, item.toString()),
                    Snackbar.LENGTH_LONG
                )
                .setAnchorView(binding.totalCount)
                .setAction(getString(R.string.undo_link)) {
                    viewModel.addItem(item.name, item.quantity, position)
                    binding.itemsList.invalidateViews()
                    Toast.makeText(
                        activity,
                        getString(R.string.undo_remove, item.name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .show()
            }
            true // returning true to consume the click event, otherwise onClick will be called next
        }

        binding.addButton.setOnClickListener {
            val input = binding.editTextItem.text.toString()
            viewModel.addItem(input)
            binding.itemsList.invalidateViews()
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
