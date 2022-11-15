package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class ItemDetailFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_item_detail, container, false)
        val itemName = view.findViewById<TextView>(R.id.itemName)
        itemName.text = ItemDetailFragmentArgs.fromBundle(requireArguments()).item?.name.toString()
        val itemQuantity = view.findViewById<TextView>(R.id.itemQuantity)
        itemQuantity.text = ItemDetailFragmentArgs.fromBundle(requireArguments()).item?.quantity.toString()
        return view
    }
}