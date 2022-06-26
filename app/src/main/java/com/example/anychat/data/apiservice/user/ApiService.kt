package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.model.dto.MessageDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @GET("/api/chat/1/messages")
    suspend fun getChatMessages(@Query("page") page: Int): List<MessageDTO>

    @Headers( "Content-Type: application/json" )
    @POST("/api/auth/register")
    suspend fun userRegistration(@Body registrationParam: RegistrationParam): Response<TokenDTO>

    @Headers( "Content-Type: application/json" )
    @POST("/api/auth/login")
    suspend fun userLogin(@Body loginParam: LoginParam): Response<TokenDTO>

    @GET("/api/user/{username}")
    suspend fun getUser(@Path(value = "username") username: String): Response<UserDTO>
}