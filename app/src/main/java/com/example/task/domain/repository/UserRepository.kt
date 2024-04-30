package com.example.task.domain.repository

import com.example.task.domain.models.TokensDomainModel
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.models.request.RegisterRequestDomainModel

interface UserRepository {

    suspend fun register(request: RegisterRequestDomainModel) : TokensDomainModel

    suspend fun getUsers(page : Int = 0) : List<UserDomainModel>
}