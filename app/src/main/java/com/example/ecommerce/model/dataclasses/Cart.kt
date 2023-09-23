package com.example.ecommerce.model.dataclasses

data class CartItem(
    val createAt: String,
    val id: Int,
    val product: Product,
    val productId: Int,
    val quantity: Int,
    val updatedAt: String,
    val userId: Int
)

data class CartRequest(
    val userId: Int,
    val productId: Int,
    val quantity: Int
)