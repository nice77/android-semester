package com.example.task.data.remote.interceptors

import com.example.task.di.ServiceLocator
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class RefreshTokenInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (chain.request().url.pathSegments.last() != "refresh_token") {
            return chain.proceed(chain.request())
        }

        val tokensEntity = ServiceLocator.providePreferences().getTokens()
        val request = Request.Builder()
            .post(tokensEntity!!.refresh.toRequestBody("application/json".toMediaType()))
            .build()
        return chain.proceed(request)
    }
}