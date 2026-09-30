package com.yatraverse.data.models

data class LoginRequest(
    val email: String,
    val password: String
)

data class LoginResponse(
    val token: String,
    val email: String,
    val fullName: String
)

data class SignupRequest(
    val fullName: String,
    val email: String,
    val password: String
)
