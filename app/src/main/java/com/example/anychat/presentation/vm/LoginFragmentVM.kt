package com.example.anychat.presentation.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.TokenDTO
import com.example.anychat.domain.model.param.LoginParam
import com.example.anychat.domain.model.param.RegistrationParam
import com.example.anychat.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import retrofit2.Response

class LoginFragmentVM(
    private val userRepository: UserRepository
) : ViewModel() {
    val tokenDTOLiveData: MutableLiveData<TokenDTO> by lazy {
        MutableLiveData<TokenDTO>()
    }

    fun userLogin(userLoginParam: LoginParam) {
        viewModelScope.launch {
            val userRegistration = userRepository.userLogin(userLoginParam)
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