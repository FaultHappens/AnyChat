package com.example.anychat.domain.repository

import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.UserUpdateParam
import retrofit2.Response

interface UserRepository {

    suspend fun getUser(username: String): Response<UserDTO>

    suspend fun updateUser(username: String,  userUpdateParam: UserUpdateParam): Response<UserDTO>
}