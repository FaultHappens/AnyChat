package com.example.anychat.domain.repository

import android.graphics.Bitmap
import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.UserUpdateParam
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Part
import retrofit2.http.Path

interface UserRepository {

    suspend fun getUser(username: String): Response<UserDTO>

    suspend fun updateUser(username: String,  userUpdateParam: UserUpdateParam): Response<UserDTO>

    suspend fun uploadPhoto(image: MultipartBody.Part): Response<Void>

    suspend fun getPhoto(photoId: String): Response<ResponseBody>
}