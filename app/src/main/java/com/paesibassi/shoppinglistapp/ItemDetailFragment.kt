package com.paesibassi.shoppinglistapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.paesibassi.shoppinglistapp.databinding.FragmentItemDetailBinding

class ItemDetailFragment : Fragment() {
    private var _binding: FragmentItemDetailBinding? = null // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentItemDetailBinding.inflate(inflater, container, false)
        val view = binding.root

        binding.itemName.text = ItemDetailFragmentArgs.fromBundle(requireArguments()).item?.name.toString()
        binding.itemQuantity.text = ItemDetailFragmentArgs.fromBundle(requireArguments()).item?.quantity.toString()
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}