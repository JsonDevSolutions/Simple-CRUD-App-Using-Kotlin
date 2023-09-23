package com.example.ecommerce.model

import com.example.ecommerce.model.dataclasses.ApiResponse
import com.example.ecommerce.model.dataclasses.CartItem
import com.example.ecommerce.model.dataclasses.CartRequest
import com.example.ecommerce.model.dataclasses.LoginRequest
import com.example.ecommerce.model.dataclasses.LoginResponse
import com.example.ecommerce.model.dataclasses.Product
import com.example.ecommerce.model.dataclasses.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
   @GET("product")
    fun getProducts(): Call<List<Product>>

    @GET("product/{id}")
    fun getProductDetails(@Path("id") id: String): Call<Product>

    @GET("cart/{userId}")
    fun getCartItems(@Path("userId") userId: String): Call<MutableList<CartItem>>

    @GET("cart/count/{userId}")
    fun getCartItemsCount(@Path("userId") userId: Number): Call<Number>

    @POST("cart")
    fun addToCart(@Body user: CartRequest): Call<ApiResponse>

    @DELETE("cart/{userId}/{id}")
    fun deleteCartItem(@Path("userId") userId: Int, @Path("id") id: Int): Call<ApiResponse>

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun signUp(@Body user: User): Call<User>
}