package com.example.viewmodel.ui.theme

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.ApiInterface
import com.example.viewmodel.ApiResponse
import com.example.viewmodel.CartItem
import com.example.viewmodel.CartRequest
import com.example.viewmodel.LoginResponse
import com.example.viewmodel.ProductItem
import com.example.viewmodel.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartViewModel: ViewModel() {
    private val baseUrl = "http://192.168.1.3:3333/"
    private var _cartItems = MutableLiveData<List<CartItem>>()
    private var _cartItemsCount = MutableLiveData<Number>()
    private var _addCartResponse = MutableLiveData<ApiResponse?>()

    val cartItems: LiveData<List<CartItem>>
        get() = _cartItems

    init {
        getCartItems()
    }

    fun cartItemsCount(userId: Number): LiveData<Number> {
        getCartItemsCount(userId)
        return _cartItemsCount
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
    private fun getCartItemsCount(userId: Number) {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getCartItemsCount(userId)
        retrofitData.enqueue(object : Callback<Number> {
            override fun onResponse(call: Call<Number>, response: Response<Number>) {
                val responseBody = response.body()!!
                _cartItemsCount.value = responseBody
            }

            override fun onFailure(call: Call<Number>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
    fun addCart(cartRequest: CartRequest) {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.addToCart(cartRequest)
        retrofitData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val responseBody = response?.body()
                if (responseBody !== null){
                    _addCartResponse.value = responseBody
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("AuthActivity", "onFailure: " + t.message)
            }
        })
    }
}