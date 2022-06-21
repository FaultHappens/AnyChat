package com.example.anychat.domain.repository

import com.example.anychat.domain.model.dto.CodeDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.model.param.ResetPasswordCodeParam
import com.example.anychat.domain.model.param.ResetPasswordParam
import retrofit2.Response
import retrofit2.http.*

interface UserRepository {

    suspend fun userRegistration(registrationParam: RegistrationParam): Response<TokenDTO>

    suspend fun userLogin(loginParam: LoginParam): Response<TokenDTO>

    suspend fun resetPasswordCode(resetPasswordCodeParam: ResetPasswordCodeParam): Response<Void>

    suspend fun resetPassword(resetPassword: ResetPasswordParam): Response<Void>

    suspend fun getUser(username: String): Response<UserDTO>

    suspend fun isUser(username: String): Response<Boolean>

    suspend fun isEmail(email: String): Response<Boolean>
}