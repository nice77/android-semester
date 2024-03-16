package com.example.task.domain.usecases

import com.example.task.data.repositories.AuthRepository
import com.example.task.data.repositories.TokensRepository
import com.example.task.domain.models.request.AuthenticationRequestDomainModel
import com.example.task.utils.runSuspendCatching

class AuthenticateUserUseCase (
    private val tokensRepository: TokensRepository,
    private val authRepository: AuthRepository
) {

    suspend operator fun invoke(
        request: AuthenticationRequestDomainModel,
    ) : Result<*> {
        return runSuspendCatching {
            val tokens = authRepository.authenticate(request)
            tokensRepository.addTokens(tokens)
        }
    }
}