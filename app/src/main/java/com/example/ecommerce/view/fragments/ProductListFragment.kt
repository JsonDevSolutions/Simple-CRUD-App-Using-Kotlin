package com.example.ecommerce.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.ecommerce.R
import com.example.ecommerce.databinding.FragmentProductListBinding
import com.example.ecommerce.datastore.LoggedInUserDetails
import com.example.ecommerce.view.adapters.ProductAdapter
import com.example.ecommerce.viewmodel.AuthViewModel
import com.example.ecommerce.viewmodel.CartViewModel
import com.example.ecommerce.viewmodel.ProductViewModel
import kotlinx.coroutines.launch

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: ProductViewModel by viewModels()
    private val viewModel1: AuthViewModel by viewModels()
    private val viewModelCart: CartViewModel by viewModels()

    private lateinit var recyclerView: RecyclerView
    private lateinit var productAdapter: ProductAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)

        viewModel.products.observe(viewLifecycleOwner
        ) { newProducts ->
            productAdapter = ProductAdapter(newProducts, viewModelCart)
            productAdapter.notifyDataSetChanged()
            recyclerView.adapter = productAdapter
        }

        viewModel1.accessToken.observe(viewLifecycleOwner
        ) { accessToken ->
            Log.d("AuthActivity", "onLogin: $accessToken")
        }

        viewModelCart.cartItemsCount().observe(viewLifecycleOwner
        ) { cartItemsCount ->
           binding.actionBar.cartCount.text = cartItemsCount.toString()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewProducts

        val viewCart = view.findViewById<ImageView>(R.id.view_cart)
        viewCart?.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToCartPageFragment()
            viewCart.findNavController().navigate(action)
        }

        val logout = view.findViewById<ImageView>(R.id.logout)
        logout?.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToLoginFragment()

            //delete user login details in datastore
            val scope = lifecycleScope
            val dataStore  = context?.let { it -> LoggedInUserDetails(it) }
            scope.launch {
                dataStore?.deleteLoggedInUserDetails()
                logout.findNavController().navigate(action)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}