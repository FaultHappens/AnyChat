package com.example.anychat.domain.koin


import android.content.Context
import com.example.anychat.data.apiservice.user.ApiService
import com.example.anychat.data.repository.UserRepositoryImpl
import com.example.anychat.domain.repository.ChatRepository
import com.example.anychat.domain.repository.UserRepository
import com.example.anychat.presentation.vm.ChatFragmentVM
import com.example.anychat.presentation.vm.LoginFragmentVM
import com.example.anychat.presentation.vm.ProfileFragmentVM
import com.example.anychat.presentation.vm.RegistrationFragmentVM
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val modules = module{
    single { provideOkHttpClient(androidContext()) }
    single { provideRetrofit(get()) }

    single {get<Retrofit>().create(ApiService::class.java)}
    single<UserRepository> { UserRepositoryImpl(get()) }
    single<ChatRepository> { ChatRepository(get()) }
    viewModel { RegistrationFragmentVM(get()) }
    viewModel { LoginFragmentVM(get())}
    viewModel { ProfileFragmentVM(get()) }
    viewModel { ChatFragmentVM(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder().baseUrl("http://192.168.191.58:8080/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(androidContext: Context): OkHttpClient {
    return OkHttpClient().newBuilder().connectTimeout(1, TimeUnit.HOURS).readTimeout(1, TimeUnit.HOURS).writeTimeout(1, TimeUnit.HOURS)
        .authenticator { _, response ->
            val accessToken = androidContext.getSharedPreferences("token", Context.MODE_PRIVATE)
                ?.getString("access_token", null)


            val responseBuilder = response.request().newBuilder()
            if (accessToken != null) {
                responseBuilder.addHeader("Authorization", "Bearer $accessToken")
            }

            responseBuilder.build()
        }.build()
}