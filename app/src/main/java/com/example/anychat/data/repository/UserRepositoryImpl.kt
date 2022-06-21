package com.example.anychat.data.repository

import com.example.anychat.data.apiservice.user.UserApiService
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.model.param.ResetPasswordCodeParam
import com.example.anychat.domain.model.param.ResetPasswordParam
import com.example.anychat.domain.repository.UserRepository
import retrofit2.Response

class UserRepositoryImpl(
    private val userApiService: UserApiService
): UserRepository {
    override suspend fun userRegistration(registrationParam: RegistrationParam): Response<TokenDTO> {
         return userApiService.userRegistration(registrationParam)
    }

    override suspend fun userLogin(loginParam: LoginParam): Response<TokenDTO> {
        return userApiService.userLogin(loginParam)
    }

    override suspend fun getUser(username: String): Response<UserDTO> {
        return userApiService.getUser(username)
    }

    override suspend fun resetPasswordCode(resetPasswordCodeParam: ResetPasswordCodeParam): Response<Void> {
        return userApiService.resetPasswordCode(resetPasswordCodeParam)
    }

    override suspend fun resetPassword(resetPassword: ResetPasswordParam): Response<Void> {
        return userApiService.resetPassword(resetPassword)
    }
}