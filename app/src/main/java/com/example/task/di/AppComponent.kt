package com.example.task.di

import android.content.Context
import com.example.task.data.di.DataModule
import com.example.task.data.di.DataModuleBinder
import com.example.task.presentation.authentication.AuthViewModel
import com.example.task.presentation.main.MainViewModel
import com.example.task.presentation.registration.RegisterViewModel
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton


@Component(modules = [
    DataModule::class,
    DataModuleBinder::class
])
@Singleton
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun provideContext(ctx : Context) : Builder

        fun build() : AppComponent
    }

    fun registerViewModel() : RegisterViewModel.Factory

    fun authViewModel() : AuthViewModel.Factory

    fun mainViewModel() : MainViewModel.Factory
}