package com.example.viewmodel

import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.fragment.app.viewModels

class ProductListFragment : Fragment() {
    private var _binding: FragmentProductListBinding? = null
    private val binding get() = _binding!!

    private lateinit var recyclerView: RecyclerView
    lateinit var productAdapter: ProductAdapter

//    private val productViewModel: ProductViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentProductListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerViewProducts

        val viewCart = view?.findViewById<TextView>(R.id.view_cart)
        viewCart?.setOnClickListener{
            val action = ProductListFragmentDirections.actionProductListFragmentToCartPageFragment()
            viewCart.findNavController().navigate(action)
        }

//        val logOut = view?.findViewById<TextView>(R.id.logout)
//        logOut?.setOnClickListener{
//            val action = ProductListFragmentDirections.ac
//            logOut?.findNavController()?.navigate(action)
//        }

        getProducts()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getProducts() {
        val baseUrl = "http://192.168.1.3:3333/"
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getProducts()
        retrofitData.enqueue(object : Callback<List<ProductItem>?> {
            override fun onResponse(
                call: Call<List<ProductItem>?>,
                response: Response<List<ProductItem>?>
            ) {

                val responseBody = response.body()!!

                productAdapter = ProductAdapter(responseBody)
                productAdapter.notifyDataSetChanged()

                recyclerView.adapter = productAdapter
            }

            override fun onFailure(call: Call<List<ProductItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}