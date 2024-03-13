package com.example.task.domain.usecases

import com.example.task.data.local.sharedpreferences.Preferences
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.AuthenticationRequest
import com.example.task.data.remote.datasource.responses.AuthenticationResponse
import com.example.task.data.remote.mappers.ToSharedPreferencesMapper
import kotlinx.coroutines.flow.MutableSharedFlow

class AuthenticateUserUseCase (
    private val preferences: Preferences,
    private val toSharedPreferencesMapper: ToSharedPreferencesMapper
) {

    suspend fun authenticate(
        request: AuthenticationRequest,
        _errorFlow : MutableSharedFlow<Throwable>
    ) {
        runCatching {
            NetworkManager.authApi.authenticate(request)
        }.onSuccess {  response ->
            val tokensEntity = toSharedPreferencesMapper.mapToTokensEntity(response)
            preferences.addTokens(tokensEntity)
        }.onFailure { exception ->
            _errorFlow.emit(exception)
        }
    }

}