package com.example.viewmodel.ui.theme

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.viewmodel.ApiInterface
import com.example.viewmodel.ApiResponse
import com.example.viewmodel.BASE_URL
import com.example.viewmodel.CartItem
import com.example.viewmodel.CartRequest
import com.example.viewmodel.LoginResponse
import com.example.viewmodel.ProductItem
import com.example.viewmodel.USER_ID
import com.example.viewmodel.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CartViewModel: ViewModel() {
    private var _cartItems = MutableLiveData<MutableList<CartItem>>()
    private var _cartItemsCount = MutableLiveData<Number>()
    private var _addCartResponse = MutableLiveData<ApiResponse?>()

    val cartItems: LiveData<MutableList<CartItem>>
        get() = _cartItems

    init {
        getCartItems()
    }

    fun cartItemsCount(): LiveData<Number> {
        getCartItemsCount()
        return _cartItemsCount
    }

    private fun getCartItems() {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getCartItems(USER_ID.toString())
        retrofitData.enqueue(object : Callback<MutableList<CartItem>?> {
            override fun onResponse(
                call: Call<MutableList<CartItem>?>,
                response: Response<MutableList<CartItem>?>
            ) {
                val responseBody = response.body()!!
                _cartItems.value = responseBody
            }

            override fun onFailure(call: Call<MutableList<CartItem>?>, t: Throwable) {
                Log.d("MainActivity", "onFailure: " + t.message)
            }
        })
    }
    private fun getCartItemsCount() {
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.getCartItemsCount(USER_ID)
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
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.addToCart(cartRequest)
        retrofitData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val responseBody = response.body()
                if (responseBody !== null){
                    _addCartResponse.value = responseBody
                    getCartItemsCount()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("AuthActivity", "onFailure: " + t.message)
            }
        })
    }

    fun deleteCartItem(id: Int) {
        Log.d("AuthActivity", "Delete Card: $id")
        val retroFitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(ApiInterface::class.java)
        val retrofitData = retroFitBuilder.deleteCartItem(USER_ID, id)
        retrofitData.enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                val responseBody = response.body()
                if (responseBody !== null){
                    _addCartResponse.value = responseBody
                    getCartItemsCount()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Log.d("AuthActivity", "onFailure: " + t.message)
            }
        })
    }
}