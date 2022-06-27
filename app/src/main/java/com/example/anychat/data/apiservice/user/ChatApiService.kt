package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.paging.ChatPagingResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ChatApiService {
    @GET("chat/1/messages")
    suspend fun getChatMessages(@Query("page") page: Int): ChatPagingResponse

    @GET("chat/1/online")
    suspend fun getOnline(): Int

}