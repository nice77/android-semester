package com.example.task.data.remote.datasource

import com.example.task.data.remote.interceptors.RefreshTokenInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private val BASE_URL = "https://97fc-94-180-239-195.ngrok-free.app/api/"

    private val okHttpClient : OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(RefreshTokenInterceptor())
            .build()
    }

    private val retrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
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