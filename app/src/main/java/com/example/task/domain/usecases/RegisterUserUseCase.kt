package com.example.task.domain.usecases

import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.RegisterRequest
import kotlinx.coroutines.flow.MutableSharedFlow

class RegisterUserUseCase {

    suspend fun register(
        request : RegisterRequest,
        _errorFlow : MutableSharedFlow<Throwable>
    ) {
        runCatching {
            NetworkManager.userApi.register(request)
        }.onFailure { exception ->
            _errorFlow.emit(exception)
        }
    }

}