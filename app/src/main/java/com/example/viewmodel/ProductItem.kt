package com.example.viewmodel

data class ProductItem(
    val createAt: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Int,
    val published: Boolean,
    val updatedAt: String
)