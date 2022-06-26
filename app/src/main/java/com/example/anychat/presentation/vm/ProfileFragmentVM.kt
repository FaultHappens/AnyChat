package com.example.anychat.presentation.vm

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.repository.UserRepository
import com.google.gson.Gson
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileFragmentVM(
    private val userRepository: UserRepository
) : ViewModel() {

    val userDTOLiveData: MutableLiveData<UserDTO> by lazy {
        MutableLiveData<UserDTO>()
    }

    val userPhotoLiveData: MutableLiveData<Bitmap> by lazy {
        MutableLiveData<Bitmap>()
    }

    fun getUser(username: String) {
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

    fun uploadPhoto(photo: MultipartBody.Part) {
        viewModelScope.launch {
            userRepository.uploadPhoto(photo)
        }
    }

    fun getPhoto(photoId: String) {
        viewModelScope.launch {
            val photoCall = userRepository.getPhoto(photoId)

            if (photoCall.isSuccessful) {
                val bmp = BitmapFactory.decodeStream(photoCall.body()?.byteStream());
                userPhotoLiveData.value = bmp
            }
        }
    }
}