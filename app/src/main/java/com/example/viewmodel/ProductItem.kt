package com.example.viewmodel

class Product : ArrayList<ProductItem>()
data class ProductItem(
    val createAt: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Int,
    val published: Boolean,
    val updatedAt: String,
    val image: String
)