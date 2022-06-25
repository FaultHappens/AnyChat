package com.example.anychat.domain.repository

import com.example.anychat.domain.model.dto.CodeDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.*
import retrofit2.Response
import retrofit2.http.*

interface UserRepository {

    suspend fun getUser(username: String): Response<UserDTO>

    suspend fun updateUser(username: String,  userUpdateParam: UserUpdateParam): Response<UserDTO>
}