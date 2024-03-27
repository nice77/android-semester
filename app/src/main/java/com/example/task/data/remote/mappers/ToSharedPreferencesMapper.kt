package com.example.task.data.remote.mappers

import com.example.task.data.local.sharedpreferences.TokensEntity
import com.example.task.data.remote.datasource.responses.AuthenticationResponse

class ToSharedPreferencesMapper {

    fun mapToTokensEntity(response: AuthenticationResponse) : TokensEntity {
        return TokensEntity(
            access = response.access,
            refresh = response.refresh
        )
    }

}