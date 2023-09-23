package com.example.ecommerce.model.dataclasses

data class Product(
    val createAt: String,
    val description: String,
    val id: Int,
    val name: String,
    val price: Int,
    val published: Boolean,
    val updatedAt: String,
    val image: String
)