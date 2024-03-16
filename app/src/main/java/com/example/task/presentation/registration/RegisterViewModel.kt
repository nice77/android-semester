package com.example.task.presentation.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.task.di.ServiceLocator
import com.example.task.domain.models.ErrorEnum
import com.example.task.domain.models.request.RegisterRequestDomainModel
import com.example.task.domain.usecases.RegisterUserUseCase
import com.example.task.domain.validators.ValidateEmailUseCase
import com.example.task.domain.validators.ValidatePasswordUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase
) : ViewModel() {

    private val _errorFlow = MutableSharedFlow<ErrorEnum>()
    val errorFlow : SharedFlow<ErrorEnum>
        get() = _errorFlow

    fun registerUser(name : String, email : String, password : String) {
        viewModelScope.launch {
            val request = RegisterRequestDomainModel(
                name = name,
                email = email,
                password = password
            )
            val result = registerUserUseCase(request)
            if (result.isFailure) {
                result.onFailure { error ->
                    when (error) {
                        is HttpException -> _errorFlow.emit(ErrorEnum.EMAIL_IN_USE)
                        is UnknownHostException -> _errorFlow.emit(ErrorEnum.UNKNOWN_HOST)
                    }
                }
            }
        }
    }

    fun validateEmail(text : String) : Boolean {
        return validateEmailUseCase(text)
    }

    fun validatePassword(text : String) : Boolean {
        return validatePasswordUseCase(text)
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val registerUserUseCase = RegisterUserUseCase(ServiceLocator.provideUserRepository())
                val validateEmailUseCase = ValidateEmailUseCase()
                val validatePasswordUseCase = ValidatePasswordUseCase()
                RegisterViewModel(
                    registerUserUseCase = registerUserUseCase,
                    validateEmailUseCase = validateEmailUseCase,
                    validatePasswordUseCase = validatePasswordUseCase
                )
            }
        }
    }
}