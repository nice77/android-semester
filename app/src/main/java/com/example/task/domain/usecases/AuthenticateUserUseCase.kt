package com.example.task.domain.usecases

import com.example.task.data.repositories.AuthRepositoryImpl
import com.example.task.data.repositories.TokensRepositoryImpl
import com.example.task.domain.models.request.AuthenticationRequestDomainModel
import com.example.task.utils.runSuspendCatching
import javax.inject.Inject

class AuthenticateUserUseCase @Inject constructor(
    private val tokensRepositoryImpl: TokensRepositoryImpl,
    private val authRepositoryImpl: AuthRepositoryImpl
) {

    suspend operator fun invoke(
        request: AuthenticationRequestDomainModel,
    ) : Result<Unit> {
        return runSuspendCatching {
            val tokens = authRepositoryImpl.authenticate(request)
            tokensRepositoryImpl.addTokens(tokens)
        }
    }
}