package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.ResetPasswordCodeParam
import com.example.anychat.domain.model.param.ResetPasswordParam
import com.example.anychat.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class PasswordResetFragmentVM(
    private val authRepository: AuthRepository
) : ViewModel() {
    val emailExistLiveData: MutableLiveData<Boolean> by lazy {
        MutableLiveData<Boolean>()
    }



    fun sendPasswordResetCode(email: String) {
         viewModelScope.launch {
            authRepository.resetPasswordCode(ResetPasswordCodeParam(email))
         }
    }
    fun sendPasswordReset(passwordResetPasswordParam: ResetPasswordParam){
        viewModelScope.launch {
            authRepository.resetPassword(passwordResetPasswordParam)
        }
    }

    fun emailExist(email: String) {
        viewModelScope.launch {
            val isEmail = authRepository.isEmail(email)
            emailExistLiveData.value = isEmail.body()
        }


    }


}