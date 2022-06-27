package com.example.anychat.data.apiservice.user


import com.example.anychat.domain.model.dto.UserDTO
import com.example.anychat.domain.model.param.UserUpdateParam
import okhttp3.MultipartBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface UserApiService {
    @GET("user/{username}")
    suspend fun getUser(@Path(value = "username") username: String): Response<UserDTO>


    @PUT("user/{username}")
    suspend fun updateUser(@Path(value = "username") username: String, @Body userUpdateParam: UserUpdateParam): Response<UserDTO>

    @Multipart
    @POST("user/photo")
    suspend fun uploadPhoto(@Part image: MultipartBody.Part): Response<Void>

    @GET("user/photo/{photoId}")
    suspend fun getPhoto(@Path("photoId") photoId: String): Response<ResponseBody>
}