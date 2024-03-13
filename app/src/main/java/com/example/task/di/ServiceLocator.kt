package com.example.task.di

import android.annotation.SuppressLint
import android.content.Context
import com.example.task.data.local.sharedpreferences.Preferences
import com.example.task.data.remote.mappers.ToSharedPreferencesMapper
import okhttp3.OkHttpClient


@SuppressLint("StaticFieldLeak")
object ServiceLocator {

    private lateinit var ctx : Context

    fun provideContext() : Context {
        return ctx ?: throw NullPointerException()
    }

    fun setContext(ctx : Context) {
        this.ctx = ctx
    }

    fun providePreferences() : Preferences {
        return Preferences(provideContext())
    }

    fun provideToSharedPreferencesMapper() : ToSharedPreferencesMapper {
        return ToSharedPreferencesMapper()
    }

}