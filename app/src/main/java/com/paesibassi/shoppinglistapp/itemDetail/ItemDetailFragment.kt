package com.paesibassi.shoppinglistapp.itemDetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.paesibassi.shoppinglistapp.database.ItemsDatabase
import com.paesibassi.shoppinglistapp.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private lateinit var viewModel: ItemDetailViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val view = binding.root
        val application = requireNotNull(this.activity).application
        val dao = ItemsDatabase.getInstance(application).itemsDao
        val itemId = ItemDetailFragmentArgs.fromBundle(requireArguments()).item
        val viewModelFactory = ItemDetailViewModelFactory(itemId, dao)
        viewModel = ViewModelProvider(this, viewModelFactory)[ItemDetailViewModel::class.java]
        binding.itemDetailViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}