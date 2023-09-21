package com.example.viewmodel.ui.theme

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.ApiInterface
import com.example.viewmodel.CartItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartViewModel: ViewModel() {
    private val baseUrl = "http://192.168.1.3:3333/"
    private var _cartItems = MutableLiveData<List<CartItem>>()

    val cartItems: LiveData<List<CartItem>>
        get() = _cartItems

    init {
        getCartItems()
    }

    private fun getCartItems() {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getCartItems("1")
        retrofitData.enqueue(object : Callback<List<CartItem>?> {
            override fun onResponse(
                call: Call<List<CartItem>?>,
                response: Response<List<CartItem>?>
            ) {
                val responseBody = response.body()!!
                _cartItems.value = responseBody
            }

            override fun onFailure(call: Call<List<CartItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
}