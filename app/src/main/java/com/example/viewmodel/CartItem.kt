package com.example.viewmodel
class Cart : ArrayList<CartItem>()
data class CartItem(
    val createAt: String,
    val id: Int,
    val product: ProductItem,
    val productId: Int,
    val quantity: Int,
    val updatedAt: String,
    val userId: Int
)
