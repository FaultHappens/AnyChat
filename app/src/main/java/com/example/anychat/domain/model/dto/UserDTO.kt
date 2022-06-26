package com.example.anychat.domain.model.dto

data class UserDTO(
    val email: String,
    val profile: String?,
    val about: String?,
    val firstname: String?,
    val lastname: String?,
    val username: String
)
