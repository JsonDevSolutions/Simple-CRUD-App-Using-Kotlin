package com.example.viewmodel

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {
   @GET(value = "product")
    fun getProducts(): Call<List<ProductItem>>

    @GET(value = "product/{id}")
    fun getProductDetails(@Path("id") id: String): Call<ProductItem>
}