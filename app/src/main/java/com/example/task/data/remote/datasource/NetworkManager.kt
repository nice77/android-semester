package com.example.task.data.remote.datasource

import com.example.task.data.remote.interceptors.RefreshTokenInterceptor
import com.example.task.data.remote.interceptors.TokenInterceptor
import com.example.task.di.ServiceLocator
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit

object NetworkManager {

    private const val _BASE_URL = "https://6a8a-94-180-239-195.ngrok-free.app/api/"
    val BASE_URL : String
        get() = _BASE_URL

    private val contentType = "application/json".toMediaType()

    private val okHttpClient : OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(RefreshTokenInterceptor(ServiceLocator.providePreferences()))
            .addInterceptor(TokenInterceptor(ServiceLocator.providePreferences()))
            .build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(_BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(contentType))
            .build()
    }

    private val _authApi : AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
    val authApi : AuthApi
        get() = _authApi

    private val _userApi : UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val userApi : UserApi
        get() = _userApi
}