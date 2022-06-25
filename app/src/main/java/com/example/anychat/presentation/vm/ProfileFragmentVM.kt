package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.repository.AuthRepository
import com.example.anychat.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class ProfileFragmentVM(
    private val userRepository: UserRepository
) : ViewModel() {

    val userDTOLiveData: MutableLiveData<UserDTO> by lazy {
        MutableLiveData<UserDTO>()
    }


    fun getUser(username: String){
        viewModelScope.launch {
            val user = userRepository.getUser(username)
            val userDTO = user.body()

            if (user.isSuccessful)
                userDTOLiveData.value = userDTO

            else {
                val errorMap =
                    Gson().fromJson(user.errorBody()?.charStream(), Map::class.java)
                println(errorMap)
            }
        }
    }



}