package com.example.ecommerce.model.dataclasses

data class LoginRequest(
    val email: String,
    val password: String
)

data class User(
    val firstName: String,
    val lastName: String,
    val email: String,
    val password: String
)