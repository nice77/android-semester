package com.example.task.data.remote.datasource

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkManager {

    private val BASE_URL = "https://97fc-94-180-239-195.ngrok-free.app/api/"

    private val okHttpClient : OkHttpClient by lazy {
        OkHttpClient.Builder()
            .build()
    }

    private val mRetrofit : Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private val _authApi : AuthApi by lazy {
        mRetrofit.create(AuthApi::class.java)
    }

    private val _userApi : UserApi by lazy {
        mRetrofit.create(UserApi::class.java)
    }

    fun getAuthApi() : AuthApi = _authApi
    fun getUserApi() : UserApi = _userApi
}