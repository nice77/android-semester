package com.example.task.data.repositories

import com.example.task.data.remote.datasource.AuthApi
import com.example.task.data.remote.datasource.requests.AuthenticationRequest
import com.example.task.data.remote.datasource.requests.RefreshRequest
import com.example.task.data.remote.mappers.ToDomainModelMapper
import com.example.task.domain.models.request.AuthenticationRequestDomainModel
import com.example.task.domain.models.TokensDomainModel
import com.example.task.domain.models.request.RefreshRequestDomainModel

class AuthRepository(
    private val authApi: AuthApi,
    private val toDomainModelMapper: ToDomainModelMapper
) {

    suspend fun authenticate(request: AuthenticationRequestDomainModel) : TokensDomainModel {
        return toDomainModelMapper.mapAuthResponseToTokensDomainModel(
            authApi.authenticate(AuthenticationRequest(email = request.email, password = request.password))
        )
    }

    suspend fun refreshToken(request: RefreshRequestDomainModel) : TokensDomainModel {
        return toDomainModelMapper.mapAuthResponseToTokensDomainModel(
            authApi.refreshToken(RefreshRequest(refresh = request.refresh))
        )
    }

}