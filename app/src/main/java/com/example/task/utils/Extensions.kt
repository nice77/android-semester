package com.example.task.utils

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.task.App
import com.example.task.di.AppComponent
import com.example.task.presentation.di.ViewModelFactory
import java.util.concurrent.CancellationException

public suspend inline fun <T> runSuspendCatching(block : () -> T) : Result<T> {
    return try {
        Result.success(block())
    } catch (e : CancellationException) {
        throw e
    } catch (e : Throwable) {
        Result.failure(e)
    }
}


public inline fun <reified T : ViewModel> Fragment.lazyViewModel(
    noinline create: (SavedStateHandle) -> T
) : Lazy<T> {
    return viewModels {
        ViewModelFactory(this, create)
    }
}

val Context.component : AppComponent
    get() = when(this) {
        is App -> component
        else -> applicationContext.component
    }