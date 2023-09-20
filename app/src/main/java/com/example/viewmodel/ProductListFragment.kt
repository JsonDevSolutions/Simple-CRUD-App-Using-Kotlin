package com.example.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.viewmodel.databinding.FragmentProductListBinding
import com.example.viewmodel.ui.theme.ProductViewModel

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false);

        viewModel.products.observe(viewLifecycleOwner
        ) { newProducts ->
            productAdapter = ProductAdapter(newProducts)
            productAdapter.notifyDataSetChanged()
            recyclerView.adapter = productAdapter
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewProducts

        val viewCart = view?.findViewById<TextView>(R.id.view_cart)
        viewCart?.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToCartPageFragment()
            viewCart.findNavController().navigate(action)
        }

        val logout = view?.findViewById<TextView>(R.id.logout)
        logout?.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToLoginFragment()
            logout.findNavController().navigate(action)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}