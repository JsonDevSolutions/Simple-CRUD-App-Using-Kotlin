package com.example.viewmodel

data class LoginResponse(
    val data: LoginData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

data class LoginData(
    val access_token: String,
    val id: Int,
    val email: String,
    val firstName: String,
    val lastName: String,
    val isAdmin: Boolean,
)