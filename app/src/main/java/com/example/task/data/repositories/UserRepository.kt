package com.example.task.data.repositories

import com.example.task.data.remote.datasource.UserApi
import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.data.remote.mappers.ToDomainModelMapper
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.models.request.RegisterRequestDomainModel

class UserRepository (
    private val userApi: UserApi,
    private val toDomainModelMapper: ToDomainModelMapper
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

    suspend fun getUsers(accessToken : String, page : Int = 0) : List<UserDomainModel> {
        return userApi.getUsers(
            "Bearer $accessToken",
            page
        ).map { userResponse ->
            toDomainModelMapper.mapUserResponseToUserDomainModel(userResponse)
        }
    }

}