package com.example.task.data.remote.interceptors

import com.example.task.data.local.sharedpreferences.TokensPreferences
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val tokensPreferences: TokensPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        if (paths.any { path -> chain.request().url.toString().contains(path) }) {
            return chain.proceed(chain.request())
        }
        val tokens = tokensPreferences.getTokens()
        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${tokens!!.access}")
            .build()

        return chain.proceed(request)
    }

    companion object {
        private val paths = listOf("/auth", "/users/new")
    }
}