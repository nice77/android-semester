package com.example.task.data.di

import android.content.Context
import android.content.SharedPreferences
import com.example.task.BuildConfig
import com.example.task.data.di.qualifiers.AccessTokenInterceptorQualifier
import com.example.task.data.di.qualifiers.AuthRetrofitQualifier
import com.example.task.data.di.qualifiers.NonAuthRetrofitQualifier
import com.example.task.data.di.qualifiers.RefreshTokenInterceptorQualifier
import com.example.task.data.local.sharedpreferences.Keys
import com.example.task.data.remote.datasource.AuthApi
import com.example.task.data.remote.datasource.EventApi
import com.example.task.data.remote.datasource.StaticStrings
import com.example.task.data.remote.datasource.UserApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class DataModule {

    @Provides
    fun provideOkHttpClient(
        @AccessTokenInterceptorQualifier accessTokenInterceptor: Interceptor,
        @RefreshTokenInterceptorQualifier refreshTokenInterceptorQualifier: Interceptor
    ) : OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(accessTokenInterceptor)
            .addInterceptor(refreshTokenInterceptorQualifier)
            .build()
    }

    @Provides
    @Singleton
    @NonAuthRetrofitQualifier
    fun provideNonAuthRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.PATH)
            .addConverterFactory(Json.asConverterFactory(StaticStrings.CONTENT_TYPE))
            .build()
    }

    @Provides
    @Singleton
    @AuthRetrofitQualifier
    fun provideAuthRetrofit(okHttpClient: OkHttpClient) : Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.PATH)
            .client(okHttpClient)
            .addConverterFactory(Json.asConverterFactory(StaticStrings.CONTENT_TYPE))
            .build()
    }

    @Provides
    @Singleton
    fun provideAuthApi(
        @NonAuthRetrofitQualifier retrofit: Retrofit
    ) : AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Singleton
    fun provideUserApi(
        @AuthRetrofitQualifier retrofit: Retrofit
    ) : UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Provides
    @Singleton
    fun provideEventApi(
        @AuthRetrofitQualifier retrofit: Retrofit
    ) : EventApi {
        return retrofit.create(EventApi::class.java)
    }

    @Provides
    @Singleton
    fun providePreferences(ctx : Context) : SharedPreferences {
        return ctx.getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
    }
}