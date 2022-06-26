package com.example.anychat.domain.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.anychat.data.apiservice.user.ApiService
import com.example.anychat.domain.paging.ChatPagingSource

class ChatRepository(private val api: ApiService) {
    fun getChatMessages() = Pager(
        pagingSourceFactory = { ChatPagingSource(api) },
        config = PagingConfig(pageSize = 10)
    ).flow
}