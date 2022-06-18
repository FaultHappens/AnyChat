package com.example.anychat.domain.model.dto

data class TokenDTO(
    val access_token: String,
    val expires_in: Long,
    val refresh_token: String,
    val refresh_expires_in: Long
)