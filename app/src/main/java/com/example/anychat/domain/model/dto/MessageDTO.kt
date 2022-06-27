package com.example.anychat.domain.model.dto

import android.graphics.Bitmap

data class MessageDTO(
    val text: String,
    val username: String,
    var profileBitmap: Bitmap? = null,
    val profile: String?,
    val createdAt: String
)