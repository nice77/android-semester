package com.example.task.data.repositories

import com.example.task.data.remote.datasource.UserApi
import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.domain.models.request.RegisterRequestDomainModel

class UserRepository (
    private val userApi: UserApi
) {
    suspend fun register(request: RegisterRequestDomainModel) {
        userApi.register(
            RegisterRequest(
                name = request.name,
                email = request.email,
                password = request.password
            )
        )
    }

}