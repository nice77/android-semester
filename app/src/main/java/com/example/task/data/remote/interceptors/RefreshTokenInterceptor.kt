package com.example.task.data.remote.interceptors

import com.example.task.data.local.sharedpreferences.TokensEntity
import com.example.task.data.local.sharedpreferences.TokensPreferences
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.RefreshRequest
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response

class RefreshTokenInterceptor(
    private val tokensPreferences: TokensPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 403) {
            val tokens = tokensPreferences.getTokens()
            val refreshRequest = RefreshRequest(tokens!!.refresh)
            val tokensRequest = chain.request().newBuilder()
                .url("${NetworkManager.BASE_URL}auth/refresh_token")
                .post(Json.encodeToString(refreshRequest).toRequestBody("application/json".toMediaType()))
                .build()
            val tokensEntity = Json.decodeFromStream<TokensEntity>(chain.proceed(tokensRequest).body!!.byteStream())
            tokensPreferences.updateTokens(tokensEntity)
            return chain.proceed(chain.request())
        }
        return response
    }
}