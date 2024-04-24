package com.example.task.data.remote.datasource

import com.example.task.data.remote.datasource.responses.EventResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface EventApi {

    @GET("events/{id}")
    @Headers(StaticStrings.AUTH_HEADER)
    suspend fun getEvent(@Path("id") id : Long) : EventResponse

    @GET("events")
    @Headers(StaticStrings.AUTH_HEADER)
    suspend fun getEvents(@Query("page") page : Int) : List<EventResponse>

    @GET("events/event-image/{fileName}")
    suspend fun getEventImage(@Path("fileName") fileName : String) : ByteArray
}