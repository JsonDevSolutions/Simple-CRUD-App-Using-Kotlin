package com.example.viewmodel

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.viewmodel.databinding.FragmentProductDetailsBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductDetailsFragment : Fragment() {
    private var _binding: FragmentProductDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var productId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.show()
        // Retrieve the LETTER from the Fragment arguments
        arguments?.let {
            productId = it.getString("productId").toString()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Retrieve and inflate the layout for this fragment
        _binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        getProductDetails();

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        (requireActivity() as? AppCompatActivity)?.supportActionBar?.hide()
        _binding = null
    }


    private fun getProductDetails() {
        val baseUrl = "http://192.168.1.3:3333/"
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getProductDetails(productId)
        retrofitData.enqueue(object : Callback<ProductItem> {

            override fun onResponse(call: Call<ProductItem>, response: Response<ProductItem>) {
                val responseBody = response.body()!!

                val txtProductName = view?.findViewById<TextView>(R.id.product_name)
                val txtDescription = view?.findViewById<TextView>(R.id.description)
                val txtPrice = view?.findViewById<TextView>(R.id.price)

                txtProductName?.text = responseBody.name
                txtDescription?.text = responseBody.description
                txtPrice?.text = "$" + responseBody.price.toString()
            }

            override fun onFailure(call: Call<ProductItem>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}