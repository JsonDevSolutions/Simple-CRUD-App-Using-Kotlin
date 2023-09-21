package com.example.viewmodel

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiInterface {
   @GET("product")
    fun getProducts(): Call<List<ProductItem>>

    @GET("product/{id}")
    fun getProductDetails(@Path("id") id: String): Call<ProductItem>

    @POST("auth/login")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    @POST("auth/register")
    fun signUp(@Body user: User): Call<User>
}