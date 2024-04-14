package com.example.task.data.di

import com.example.task.data.di.qualifiers.AccessTokenInterceptorQualifier
import com.example.task.data.di.qualifiers.RefreshTokenInterceptorQualifier
import com.example.task.data.remote.interceptors.AccessTokenInterceptor
import com.example.task.data.remote.interceptors.RefreshTokenInterceptor
import dagger.Binds
import dagger.Module
import okhttp3.Interceptor

@Module
interface DataModuleBinder {

    @Binds
    @AccessTokenInterceptorQualifier
    fun bindAccessTokenInterceptorToInterface(accessTokenInterceptor: AccessTokenInterceptor) : Interceptor

    @Binds
    @RefreshTokenInterceptorQualifier
    fun bindRefreshTokenInterceptorToInterface(refreshTokenInterceptor: RefreshTokenInterceptor) : Interceptor

}