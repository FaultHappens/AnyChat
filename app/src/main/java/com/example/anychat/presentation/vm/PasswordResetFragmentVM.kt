package com.example.anychat.presentation.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.param.ResetPasswordCodeParam
import com.example.anychat.domain.model.param.ResetPasswordParam
import com.example.anychat.domain.repository.AuthRepository
import kotlinx.coroutines.launch

class PasswordResetFragmentVM(
    private val authRepository: AuthRepository
) : ViewModel() {
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


}