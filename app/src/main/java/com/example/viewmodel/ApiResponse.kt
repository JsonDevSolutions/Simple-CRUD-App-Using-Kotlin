package com.example.viewmodel

data class LoginResponse(
    val data: LoginData,
    val message: String,
    val statusCode: Int,
    val success: Boolean
)

data class LoginData(
    val access_token: String,
    val email: String,
    val firstName: String,
    val id: Int,
    val isAdmin: Boolean,
    val lastName: String
)