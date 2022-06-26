package com.example.anychat.data.repository

import com.example.anychat.data.apiservice.user.UserApiService
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.UserUpdateParam
import com.example.anychat.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val userApiServer: UserApiService
): UserRepository {
    override suspend fun getUser(username: String): Response<UserDTO> = userApiServer.getUser(username)


    override suspend fun updateUser(
        username: String,
        userUpdateParam: UserUpdateParam
    ): Response<UserDTO>  = userApiServer.updateUser(username,userUpdateParam)

}