package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.model.dto.CodeDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.model.param.ResetPasswordCodeParam
import com.example.anychat.domain.model.param.ResetPasswordParam
import retrofit2.Response
import retrofit2.http.*

interface AuthApiService {

    @Headers( "Content-Type: application/json" )
    @POST("auth/register")
    suspend fun userRegistration(@Body registrationParam: RegistrationParam): Response<TokenDTO>

    @Headers( "Content-Type: application/json" )
    @POST("auth/login")
    suspend fun userLogin(@Body loginParam: LoginParam): Response<TokenDTO>

    @Headers( "Content-Type: application/json" )
    @POST("auth/reset-password-code")
    suspend fun resetPasswordCode(@Body resetPasswordCodeParam: ResetPasswordCodeParam): Response<Void>

    @Headers( "Content-Type: application/json" )
    @POST("auth/reset-password")
    suspend fun resetPassword(@Body resetPassword: ResetPasswordParam): Response<Void>


    @GET("auth/is-user/{username}")
    suspend fun isUser(@Path(value = "username") username: String): Response<Boolean>

    @GET("auth/is-email/{email}")
    suspend fun isEmail(@Path(value = "email") email: String): Response<Boolean>
}