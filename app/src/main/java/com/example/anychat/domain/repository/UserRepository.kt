package com.example.anychat.domain.repository

import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserRepository {

    suspend fun userRegistration(registrationParam: RegistrationParam): Response<TokenDTO>

    suspend fun userLogin(loginParam: LoginParam): Response<TokenDTO>

    suspend fun getUser(username: String): Response<UserDTO>
}