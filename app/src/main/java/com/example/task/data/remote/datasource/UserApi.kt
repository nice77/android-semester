package com.example.task.data.remote.datasource

import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.data.remote.datasource.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("users/new")
    suspend fun register(@Body request : RegisterRequest)

    @GET("users")
    suspend fun getUsers(
        @Header("Authorization") accessToken : String = "",
        @Query("page") page : Int
    ) : List<UserResponse>
}