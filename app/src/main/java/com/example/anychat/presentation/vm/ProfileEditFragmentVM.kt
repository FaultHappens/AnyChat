package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.UserUpdateParam
import com.example.anychat.domain.repository.AuthRepository
import com.example.anychat.domain.repository.UserRepository
import kotlinx.coroutines.launch

class ProfileEditFragmentVM(
    private val userRepository: UserRepository
) : ViewModel() {

    val userDTOLiveData: MutableLiveData<UserDTO> by lazy {
        MutableLiveData<UserDTO>()
    }


    fun updateUser(username: String ,userUpdateParam: UserUpdateParam){
        viewModelScope.launch {
            userRepository.updateUser(username,userUpdateParam)
        }
    }
    fun getUser(username: String){
        viewModelScope.launch {
            val user = userRepository.getUser(username)
            if (user.isSuccessful)
                userDTOLiveData.value = user.body()
        }
    }




}