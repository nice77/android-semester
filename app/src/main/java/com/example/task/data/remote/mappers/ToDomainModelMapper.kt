package com.example.task.data.remote.mappers

import com.example.task.data.remote.datasource.responses.AuthenticationResponse
import com.example.task.domain.models.TokensDomainModel

class ToDomainModelMapper {

    fun mapToDomainModel(response : AuthenticationResponse) : TokensDomainModel {
        return TokensDomainModel(
            access = response.access,
            refresh = response.refresh
        )
    }

}