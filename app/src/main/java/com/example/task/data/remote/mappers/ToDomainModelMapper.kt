package com.example.task.data.remote.mappers

import com.example.task.data.remote.datasource.responses.AuthenticationResponse
import com.example.task.data.remote.datasource.responses.UserResponse
import com.example.task.domain.models.TokensDomainModel
import com.example.task.domain.models.UserDomainModel

class ToDomainModelMapper {

    fun mapAuthResponseToTokensDomainModel(response : AuthenticationResponse) : TokensDomainModel {
        return TokensDomainModel(
            access = response.access,
            refresh = response.refresh
        )
    }

    fun mapUserResponseToUserDomainModel(response : UserResponse) : UserDomainModel {
        return UserDomainModel(
            id = response.id,
            name = response.name,
            age = response.age,
            email = response.email,
            city = response.city,
            userImage = response.userImage
        )
    }

}