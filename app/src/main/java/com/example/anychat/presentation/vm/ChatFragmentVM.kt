package com.example.anychat.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.anychat.domain.model.dto.MessageDTO
import com.example.anychat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow

class ChatFragmentVM(
    private var repository: ChatRepository
) : ViewModel() {

    fun getChatMessages(): Flow<PagingData<MessageDTO>> {
        return repository.getChatMessages().cachedIn(viewModelScope)
    }
}