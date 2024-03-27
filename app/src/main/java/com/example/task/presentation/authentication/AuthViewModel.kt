package com.example.task.presentation.authentication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.task.di.ServiceLocator
import com.example.task.domain.models.AuthErrorEnum
import com.example.task.domain.models.RegisterErrorEnum
import com.example.task.domain.models.request.AuthenticationRequestDomainModel
import com.example.task.domain.usecases.AuthenticateUserUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class AuthViewModel(
    private val authenticateUserUseCase: AuthenticateUserUseCase
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<AuthErrorEnum>()
    val errorFlow : SharedFlow<AuthErrorEnum>
        get() = _errorFlow

    fun authenticateUser(email : String, password: String) {
        val request = AuthenticationRequestDomainModel (
            email = email,
            password = password
        )
        viewModelScope.launch {
            val result = authenticateUserUseCase(request)
            result.onFailure { exception ->
                when (exception) {
                    is HttpException -> _errorFlow.emit(AuthErrorEnum.WRONG_CREDENTIALS)
                    is UnknownHostException -> _errorFlow.emit(AuthErrorEnum.UNKNOWN_HOST)
                }
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