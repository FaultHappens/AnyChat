package com.example.anychat.domain.paging

import com.example.anychat.domain.model.dto.MessageDTO

data class ChatPagingResponse(
    val content: List<MessageDTO>,
    val empty: Boolean,
    val first: Boolean,
    val last: Boolean,
    val number: Int,
    val numberOfElements: Int,
    val pageable: Pageable,
    val size: Int,
    val sort: SortX,
    val totalElements: Int,
    val totalPages: Int
)

data class Pageable(
    val offset: Int,
    val pageNumber: Int,
    val pageSize: Int,
    val paged: Boolean,
    val sort: Sort,
    val unpaged: Boolean
)

data class SortX(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)

data class Sort(
    val empty: Boolean,
    val sorted: Boolean,
    val unsorted: Boolean
)