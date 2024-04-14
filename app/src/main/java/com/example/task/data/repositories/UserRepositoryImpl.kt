package com.example.task.data.repositories

import com.example.task.data.remote.datasource.UserApi
import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.data.mapper.ToDomainModelMapper
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.models.request.RegisterRequestDomainModel
import com.example.task.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val userApi: UserApi,
    private val toDomainModelMapper: ToDomainModelMapper
) : UserRepository {
    override suspend fun register(request: RegisterRequestDomainModel) {
        userApi.register(
            RegisterRequest(
                name = request.name,
                email = request.email,
                password = request.password
            )
        )
    }

    override suspend fun getUsers(page : Int) : List<UserDomainModel> {
        return userApi.getUsers(
            page = page
        ).map { userResponse ->
            toDomainModelMapper.mapUserResponseToUserDomainModel(userResponse)
        }
    }

}