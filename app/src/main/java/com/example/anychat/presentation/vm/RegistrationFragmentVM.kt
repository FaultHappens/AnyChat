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

    fun userRegistration(registrationParam: RegistrationParam) {
        viewModelScope.launch {
            val userRegistration = userRepository.userRegistration(registrationParam)
            val tokenDTO = userRegistration.body()

            if (userRegistration.isSuccessful)
                tokenDTOLiveData.value = tokenDTO
            else {
                val errorMap =
                    Gson().fromJson(userRegistration.errorBody()?.charStream(), Map::class.java)
                println(errorMap)
            }
        }
    }
}