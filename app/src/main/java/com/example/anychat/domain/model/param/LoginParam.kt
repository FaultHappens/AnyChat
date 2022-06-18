package com.example.anychat.domain.model.param



data class LoginParam(
    val username: String,
    val password: String,
    val rememberMe: Boolean
)