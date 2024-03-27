package com.example.task.di

import android.annotation.SuppressLint
import android.content.Context
import com.example.task.data.local.sharedpreferences.TokensPreferences
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.mappers.ToDomainModelMapper
import com.example.task.data.remote.mappers.ToSharedPreferencesMapper
import com.example.task.data.repositories.AuthRepository
import com.example.task.data.repositories.TokensRepository
import com.example.task.data.repositories.UserRepository
import com.example.task.data.local.sharedpreferences.Keys
import com.example.task.data.local.sharedpreferences.TokensEntity


@SuppressLint("StaticFieldLeak")
object ServiceLocator {

    private lateinit var ctx : Context

    fun provideContext() : Context {
        return ctx
    }

    fun setContext(ctx : Context) {
        this.ctx = ctx
    }

    fun providePreferences() : TokensPreferences {
        return TokensPreferences(provideContext().getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE))
    }

    fun provideToDomainModelMapper() : ToDomainModelMapper = ToDomainModelMapper()

    fun provideAuthRepository() : AuthRepository {
        return AuthRepository(NetworkManager.authApi, provideToDomainModelMapper())
    }

    fun provideUserRepository() : UserRepository {
        return UserRepository(NetworkManager.userApi, provideToDomainModelMapper())
    }

    fun provideTokensRepository() : TokensRepository {
        return TokensRepository(providePreferences())
    }
}