package com.example.anychat.data.repository

import com.example.anychat.data.apiservice.user.AuthApiService
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.model.param.ResetPasswordCodeParam
import com.example.anychat.domain.model.param.ResetPasswordParam
import com.example.anychat.domain.repository.AuthRepository
import retrofit2.Response

class AuthRepositoryImpl(
    private val authApiServer: AuthApiService
): AuthRepository {
    override suspend fun userRegistration(registrationParam: RegistrationParam): Response<TokenDTO> {
         return authApiServer.userRegistration(registrationParam)
    }

    override suspend fun userLogin(loginParam: LoginParam): Response<TokenDTO> {
        return authApiServer.userLogin(loginParam)
    }

    override suspend fun isUser(username: String): Response<Boolean> {
      return  authApiServer.isUser(username)
    }

    override suspend fun isEmail(email: String): Response<Boolean> {
      return authApiServer.isEmail(email)
    }

    override suspend fun resetPasswordCode(resetPasswordCodeParam: ResetPasswordCodeParam): Response<Void> {
        return authApiServer.resetPasswordCode(resetPasswordCodeParam)
    }

    override suspend fun resetPassword(resetPassword: ResetPasswordParam): Response<Void> {
        return authApiServer.resetPassword(resetPassword)
    }
}