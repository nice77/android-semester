package com.example.task.data.remote.datasource

import com.example.task.data.remote.datasource.requests.RegisterRequest
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {

    @POST("users/new")
    suspend fun register(@Body request : RegisterRequest)

}