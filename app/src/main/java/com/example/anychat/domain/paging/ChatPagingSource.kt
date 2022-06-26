package com.example.anychat.domain.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.anychat.data.apiservice.user.ChatApiService
import com.example.anychat.domain.model.dto.MessageDTO


private const val FIRST_PAGE = 0;

class ChatPagingSource(private val service: ChatApiService):  PagingSource<Int, MessageDTO>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MessageDTO> {
        return try {
            val page = params.key ?: FIRST_PAGE
            val response = service.getChatMessages(page)
            LoadResult.Page(data = response.content, prevKey = if (page == FIRST_PAGE) null else page - 1, nextKey = if (response.content.isEmpty()) null else page + 1)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, MessageDTO>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}