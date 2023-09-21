package com.example.viewmodel

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