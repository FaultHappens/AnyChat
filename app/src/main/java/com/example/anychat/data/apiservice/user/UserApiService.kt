package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserApiService {

    @Headers( "Content-Type: application/json" )
    @POST("/api/auth/register")
    suspend fun userRegistration(@Body registrationParam: RegistrationParam): Response<TokenDTO>

    @Headers( "Content-Type: application/json" )
    @POST("/api/auth/login")
    suspend fun userLogin(@Body loginParam: LoginParam): Response<TokenDTO>
}