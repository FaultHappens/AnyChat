package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch

class RegistrationFragmentVM(
    private val userRepository: UserRepository
) : ViewModel() {
    val tokenDTOLiveData: MutableLiveData<TokenDTO> by lazy {
        MutableLiveData<TokenDTO>()
    }
   val userExistLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }
    val emailExistLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun userRegistration(registrationParam: RegistrationParam) {
        viewModelScope.launch {
            val userRegistration = userRepository.userRegistration(registrationParam)
            val tokenDTO = userRegistration.body()

            if (userRegistration.isSuccessful)
                tokenDTOLiveData.value = tokenDTO

        }
    }
    fun userExist(username: String){
        viewModelScope.launch {
            val userExist = userRepository.isUser(username)
            val userExistBoolean = userExist.body()
            userExistBoolean?.let {
                userExistLiveData.value = it
            }
        }
    }
    fun emailExist(email: String){
        viewModelScope.launch {
            val emailExist = userRepository.isEmail(email)
            val emailExistBoolean = emailExist.body()
            emailExistBoolean?.let {
                emailExistLiveData.value = it
            }
        }
    }
}