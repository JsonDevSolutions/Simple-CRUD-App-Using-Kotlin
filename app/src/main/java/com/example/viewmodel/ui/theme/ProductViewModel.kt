package com.example.viewmodel.ui.theme

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.ApiInterface
import com.example.viewmodel.ProductItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductViewModel: ViewModel() {
    private var _products = MutableLiveData<List<ProductItem>>()
    private var _productDetails = MutableLiveData<ProductItem>()
    private val BASE_URL = "http://192.168.1.3:3333/"

    val products: LiveData<List<ProductItem>>
        get() = _products

//    val productDetails: LiveData<ProductItem>
//        get() = _productDetails

    fun productDetails(productId: String): LiveData<ProductItem> {
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
        retrofitData.enqueue(object : Callback<List<ProductItem>?> {
            override fun onResponse(
                call: Call<List<ProductItem>?>,
                response: Response<List<ProductItem>?>
            ) {

                val responseBody = response.body()!!
                _products.value = responseBody
            }

            override fun onFailure(call: Call<List<ProductItem>?>, t: Throwable) {
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
        retrofitData.enqueue(object : Callback<ProductItem> {

            override fun onResponse(call: Call<ProductItem>, response: Response<ProductItem>) {
                val responseBody = response.body()!!
                _productDetails.value = responseBody
            }

            override fun onFailure(call: Call<ProductItem>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}