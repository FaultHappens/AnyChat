package com.example.anychat.domain.model.dto

data class MessageDTO(
    val text: String,
    val username: String,
    val profile: String?,
    val createdAt: String
)