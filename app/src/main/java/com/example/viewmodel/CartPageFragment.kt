package com.example.viewmodel

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodel.databinding.FragmentCartPageBinding
import com.example.viewmodel.databinding.FragmentProductListBinding
import com.example.viewmodel.ui.theme.CartViewModel
import com.example.viewmodel.ui.theme.ProductViewModel

class CartPageFragment : Fragment() {
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var recyclerView: RecyclerView

    private var _binding: FragmentCartPageBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.cartItems.observe(viewLifecycleOwner
        ) { cartItems ->
            cartAdapter = CartAdapter(cartItems, viewModel)
            cartAdapter.notifyDataSetChanged()
            recyclerView.adapter = cartAdapter
        }
        _binding = FragmentCartPageBinding.inflate(inflater, container, false)

        viewModel.cartItemsCount().observe(viewLifecycleOwner
        ) { cartItemsCount ->
            binding.actionBar.cartCount.text = cartItemsCount.toString()
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val home = view.findViewById<TextView>(R.id.txtLogo)
        home?.setOnClickListener{
            val action = CartPageFragmentDirections.actionCartPageFragmentToProductListFragment()
            home.findNavController().navigate(action)
        }
        recyclerView = binding.recyclerCartItems
    }
}