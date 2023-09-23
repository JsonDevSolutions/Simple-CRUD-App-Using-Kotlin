package com.example.ecommerce.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ecommerce.model.ApiInterface
import com.example.ecommerce.BASE_URL
import com.example.ecommerce.model.dataclasses.Product
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel: ViewModel() {
    private var _products = MutableLiveData<List<Product>>()
    private var _productDetails = MutableLiveData<Product>()

    val products: LiveData<List<Product>>
        get() = _products

//    val productDetails: LiveData<ProductItem>
//        get() = _productDetails

    fun productDetails(productId: String): LiveData<Product> {
        getProductDetails(productId)
        return _productDetails
    }

    init {
        getProducts()
    }

    private fun getProducts() {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getProducts()
        retrofitData.enqueue(object : Callback<List<Product>?> {
            override fun onResponse(
                call: Call<List<Product>?>,
                response: Response<List<Product>?>
            ) {

                val responseBody = response.body()!!
                _products.value = responseBody
            }

            override fun onFailure(call: Call<List<Product>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }

    private fun getProductDetails(productId: String) {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getProductDetails(productId)
        retrofitData.enqueue(object : Callback<Product> {

            override fun onResponse(call: Call<Product>, response: Response<Product>) {
                val responseBody = response.body()!!
                _productDetails.value = responseBody
            }

            override fun onFailure(call: Call<Product>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}