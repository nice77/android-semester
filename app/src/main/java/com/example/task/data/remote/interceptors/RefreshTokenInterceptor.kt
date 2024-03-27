package com.example.task.data.remote.interceptors

import com.example.task.data.local.sharedpreferences.TokensEntity
import com.example.task.data.local.sharedpreferences.TokensPreferences
import com.example.task.data.remote.datasource.AuthApi
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.RefreshRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class RefreshTokenInterceptor(
    private val tokensPreferences: TokensPreferences,
    private val authApi: AuthApi
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        if (response.code == 403) {
            println("TEST TAG - token expired")
            val tokens = tokensPreferences.getTokens()
            println("TEST TAG - got tokens: $tokens")
            tokens?.let {
                val refreshRequest = RefreshRequest(tokens.refresh)
                val authenticationResponseResult = runBlocking(Dispatchers.IO) {
                    runCatching {
                        authApi.refreshToken(refreshRequest)
                    }.getOrNull()
                }
                println("TEST TAG - auth Response: $authenticationResponseResult")
                authenticationResponseResult?.let {
                    val tokensEntity = TokensEntity(
                        access = it.access,
                        refresh = it.refresh
                    )
                    println("TEST TAG - Updating tokens")
                    tokensPreferences.updateTokens(tokensEntity)
                    response.close()
                    println("TEST TAG - first response closed")
                    return chain.proceed(
                        Request.Builder()
                            .url(chain.request().url)
                            .addHeader("Authorization", "Bearer ${tokensEntity.access}")
                            .build()
                    )
                }
                /* response == null ->
                we've got a handled error on server side: UsedTokenException | ExpiredJwtException | SignatureException | MalformedJwtException
                have to send user to auth page from current fragment
                 */
            }
        }
        println("TEST TAG - returning main response")
        return response
    }
}