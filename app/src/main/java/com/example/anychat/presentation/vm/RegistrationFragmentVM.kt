package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class RegistrationFragmentVM(
    private val authRepository: AuthRepository
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
            val userRegistration = authRepository.userRegistration(registrationParam)
            val tokenDTO = userRegistration.body()

            if (userRegistration.isSuccessful)
                tokenDTOLiveData.value = tokenDTO

        }
    }
    fun userExist(username: String){
        viewModelScope.launch {
            val userExist = authRepository.isUser(username)
            val userExistBoolean = userExist.body()
            userExistBoolean?.let {
                userExistLiveData.value = it
            }
        }
    }
    fun emailExist(email: String){
        viewModelScope.launch {
            val emailExist = authRepository.isEmail(email)
            val emailExistBoolean = emailExist.body()
            emailExistBoolean?.let {
                emailExistLiveData.value = it
            }
        }
    }
}