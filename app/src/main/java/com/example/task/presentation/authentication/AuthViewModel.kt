package com.example.task.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.task.di.ServiceLocator
import com.example.task.domain.models.ErrorEnum
import com.example.task.domain.models.request.AuthenticationRequestDomainModel
import com.example.task.domain.usecases.AuthenticateUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<ErrorEnum>()
    val errorFlow : SharedFlow<ErrorEnum>
        get() = _errorFlow

    fun authenticateUser(email : String, password: String) {
        val request = AuthenticationRequestDomainModel (
            email = email,
            password = password
        )
        viewModelScope.launch {
            val result = authenticateUserUseCase(request)
            if (result.isFailure) {
                _errorFlow.emit(ErrorEnum.UNKNOWN_HOST)
            }
        }
    }

    companion object {
        val factory : ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val authenticateUserUseCase =
                    AuthenticateUserUseCase(
                        tokensRepository = ServiceLocator.provideTokensRepository(),
                        authRepository = ServiceLocator.provideAuthRepository()
                    )
                AuthViewModel(authenticateUserUseCase)
            }
        }
    }
}