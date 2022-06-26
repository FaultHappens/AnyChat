package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.model.dto.MessageDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.paging.ChatPagingResponse
import com.example.anychat.domain.paging.ChatPagingSource
import retrofit2.Response
import retrofit2.http.*

interface ChatApiService {
    @GET("chat/1/messages")
    suspend fun getChatMessages(@Query("page") page: Int): ChatPagingResponse
}