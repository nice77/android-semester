package com.example.task.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.task.data.local.sharedpreferences.Preferences
import com.example.task.data.remote.datasource.requests.AuthenticationRequest
import com.example.task.data.remote.mappers.ToSharedPreferencesMapper
import com.example.task.di.ServiceLocator
import com.example.task.domain.usecases.AuthenticateUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow : SharedFlow<Throwable>
        get() = _errorFlow

    fun authenticateUser(email : String, password: String) {
        val request = AuthenticationRequest(
            email = email,
            password = password
        )
        viewModelScope.launch {
            authenticateUserUseCase.authenticate(request, _errorFlow)
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authenticateUserUseCase =
                    AuthenticateUserUseCase(
                        preferences = ServiceLocator.providePreferences(),
                        toSharedPreferencesMapper = ServiceLocator.provideToSharedPreferencesMapper()
                    )
                AuthViewModel(authenticateUserUseCase)
            }
        }
    }
}