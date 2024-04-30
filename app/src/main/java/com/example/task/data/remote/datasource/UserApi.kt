package com.example.task.data.remote.datasource

import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.data.remote.datasource.responses.AuthenticationResponse
import com.example.task.data.remote.datasource.responses.UserResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Query

interface UserApi {

    @POST("users/new")
    suspend fun register(@Body request : RegisterRequest) : AuthenticationResponse

    @GET("users")
    @Headers(StaticStrings.AUTH_HEADER)
    suspend fun getUsers(
        @Query("page") page : Int
    ) : List<UserResponse>
}