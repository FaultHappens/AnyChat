package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.model.dto.CodeDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.*
import retrofit2.Response
import retrofit2.http.*

interface UserApiServer {


    @GET("user/{username}")
    suspend fun getUser(@Path(value = "username") username: String): Response<UserDTO>


    @PUT("user/{username}")
    suspend fun updateUser(@Path(value = "username") username: String, @Body userUpdateParam: UserUpdateParam): Response<UserDTO>
}