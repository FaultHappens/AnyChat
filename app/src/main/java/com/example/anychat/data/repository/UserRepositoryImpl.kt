package com.example.anychat.data.repository

import com.example.anychat.data.apiservice.user.AuthApiServer
import com.example.anychat.data.apiservice.user.UserApiServer
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.*
import com.example.anychat.domain.repository.AuthRepository
import com.example.anychat.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val userApiServer: UserApiServer
): UserRepository {
    override suspend fun getUser(username: String): Response<UserDTO> = userApiServer.getUser(username)


    override suspend fun updateUser(
        username: String,
        userUpdateParam: UserUpdateParam
    ): Response<UserDTO>  = userApiServer.updateUser(username,userUpdateParam)

}