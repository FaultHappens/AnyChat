package com.example.anychat.domain.koin


import com.example.anychat.data.apiservice.user.UserApiService
import com.example.anychat.data.repository.UserRepositoryImpl
import com.example.anychat.domain.repository.UserRepository
import com.example.anychat.presentation.vm.LoginFragmentVM
import com.example.anychat.presentation.vm.RegistrationFragmentVM
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val modules = module{
    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }
    single {get<Retrofit>().create(UserApiService::class.java)}
    single<UserRepository> { UserRepositoryImpl(get()) }
    viewModel { RegistrationFragmentVM(get()) }
    viewModel { LoginFragmentVM(get())}
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder().baseUrl("http://192.168.100.34:8080/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder().connectTimeout(1, TimeUnit.HOURS).readTimeout(1, TimeUnit.HOURS).writeTimeout(1, TimeUnit.HOURS).build()
}