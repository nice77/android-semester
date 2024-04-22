package com.example.task

import android.app.Application
import com.example.task.di.AppComponent
import com.example.task.di.DaggerAppComponent

class App : Application() {

    lateinit var component : AppComponent

    override fun onCreate() {
        super.onCreate()
        component = DaggerAppComponent.builder()
            .provideContext(applicationContext)
            .build()
    }

}