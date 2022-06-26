package com.example.anychat.domain.koin


import android.content.Context
import com.example.anychat.data.apiservice.user.AuthApiService
import com.example.anychat.data.apiservice.user.ChatApiService
import com.example.anychat.data.apiservice.user.UserApiService
import com.example.anychat.data.repository.AuthRepositoryImpl
import com.example.anychat.data.repository.UserRepositoryImpl
import com.example.anychat.domain.repository.AuthRepository
import com.example.anychat.domain.repository.ChatRepository
import com.example.anychat.domain.repository.UserRepository
import com.example.anychat.presentation.vm.*
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

    single {get<Retrofit>().create(AuthApiService::class.java)}
    single<AuthRepository> { AuthRepositoryImpl(get()) }

    single {get<Retrofit>().create(UserApiService::class.java)}
    single<UserRepository> { UserRepositoryImpl(get()) }

    single {get<Retrofit>().create(ChatApiService::class.java)}
    single { ChatRepository(get()) }
    viewModel { RegistrationFragmentVM(get()) }
    viewModel { LoginFragmentVM(get())}
    viewModel { ProfileFragmentVM(get()) }
    viewModel { PasswordResetFragmentVM(get()) }
    viewModel { ProfileEditFragmentVM(get())}
    viewModel { ChatFragmentVM(get())}
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {

    return Retrofit.Builder().baseUrl("http://192.168.100.34:8080/api/").client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create()).build()
}

fun provideOkHttpClient(androidContext: Context): OkHttpClient {
    return OkHttpClient().newBuilder().connectTimeout(1, TimeUnit.HOURS).readTimeout(1, TimeUnit.HOURS).writeTimeout(1, TimeUnit.HOURS)
        .addInterceptor { interceptor ->
            val tokenPreference =
                androidContext.getSharedPreferences("token", Context.MODE_PRIVATE)
            val accessToken = tokenPreference
                ?.getString("access_token", null)


            val responseBuilder = interceptor.request().newBuilder()
            if (accessToken != null) {
                responseBuilder.addHeader("Authorization", "Bearer $accessToken")
            }

            interceptor.proceed(responseBuilder.build())
        }
        .followRedirects(false)
        .followSslRedirects(false)
        .build()
}