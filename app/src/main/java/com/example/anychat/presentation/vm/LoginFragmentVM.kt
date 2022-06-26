package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class LoginFragmentVM(
    private val authRepository: AuthRepository
) : ViewModel() {
    val tokenDTOLiveData: MutableLiveData<TokenDTO> by lazy {
        MutableLiveData<TokenDTO>()
    }
    val wrongCredentialLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }

    fun userLogin(userLoginParam: LoginParam) {
        viewModelScope.launch {
            val userRegistration = authRepository.userLogin(userLoginParam)
            val tokenDTO = userRegistration.body()

            if (userRegistration.isSuccessful)
                tokenDTOLiveData.value = tokenDTO
            else {
                wrongCredentialLiveData.value = true
            }
        }
    }

}