package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.anychat.domain.model.dto.MessageDTO
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatFragmentVM(
    private var repository: ChatRepository
) : ViewModel() {

    val onlineLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    fun getChatMessages(): Flow<PagingData<MessageDTO>> {
        return repository.getChatMessages().cachedIn(viewModelScope)
    }

    fun getOnline() {
        viewModelScope.launch {
            val online = repository.getOnline()
            onlineLiveData.value =  online
        }
    }
}