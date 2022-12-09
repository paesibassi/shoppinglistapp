package com.paesibassi.shoppinglistapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceManager
import com.google.android.material.snackbar.Snackbar
import com.paesibassi.shoppinglistapp.databinding.FragmentItemsListBinding

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
        val application = requireNotNull(this.activity).application
        val dao = ItemsDatabase.getInstance(application).itemsDao
        val viewModelFactory = ItemsListViewModelFactory(dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemsListViewModel::class.java]
        binding.itemsListViewModel = viewModel // data binding
        binding.lifecycleOwner = viewLifecycleOwner // live data for data binding

        // name preference shown in UI
        val namePref = PreferenceManager.getDefaultSharedPreferences(requireActivity()).getString("name", null)
        namePref?.let {
            val hearthsPref = PreferenceManager.getDefaultSharedPreferences(requireActivity()).getBoolean("hearts", false)
            val nameStr = if (hearthsPref) "$namePref \u2661" else "$namePref"
            binding.userName.text = getString(R.string.username, nameStr)
        }

        val itemClickListener: (item: Item) -> Unit = { item ->
//            val action = ItemsListFragmentDirections.actionItemsListFragmentToItemDetailFragment(item.Id)
//            view.findNavController().navigate(action)
            viewModel.markItemDone(item)
        }

        val itemLongClickListener: (item: Item) -> Boolean = { item ->
            viewModel.removeItem(item)
            Snackbar.make(
                view,
                getString(R.string.removed_item, item.name),
                Snackbar.LENGTH_LONG
            )
                .setAnchorView(binding.totalCount)
                .setAction(getString(R.string.undo_link)) {
                    viewModel.addItem(item.name, item.quantity)
                    Toast.makeText(
                        activity,
                        getString(R.string.undo_remove, item.name),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                .show()
            true // returning true to consume the click event, otherwise onClick will be called next
        }

        val itemPlusButtonClickListener: (item: Item) -> Unit = { item ->
            viewModel.incrementQuantityForItem(item)
        }

        val itemMinusButtonClickListener: (item: Item) -> Unit = { item ->
            viewModel.decrementQuantityForItem(item)
        }

        // RecyclerView
        val adapter = ItemsAdapter(
            itemClickListener,
            itemLongClickListener,
            itemPlusButtonClickListener,
            itemMinusButtonClickListener
        )
        binding.itemsList.adapter = adapter
        viewModel.items.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        // editText
        binding.editTextItem.setOnEditorActionListener { _, actionID, _ ->
            if (actionID == EditorInfo.IME_ACTION_DONE) {
                val input = binding.editTextItem.text.toString()
                viewModel.addItem(input)
                binding.editTextItem.text.clear()
            }
            true
        }
        // addButton
        binding.addButton.setOnClickListener {
            val input = binding.editTextItem.text.toString()
            viewModel.addItem(input)
            binding.editTextItem.text.clear()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
