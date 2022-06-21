package com.example.anychat.domain.model.param

data class ResetPasswordParam(
    val email: String,
    val code: String,
    val password: String
)
