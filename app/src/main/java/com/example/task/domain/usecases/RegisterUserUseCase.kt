package com.example.task.domain.usecases

import com.example.task.data.repositories.UserRepository
import com.example.task.domain.models.request.RegisterRequestDomainModel
import com.example.task.utils.runSuspendCatching

class RegisterUserUseCase (
    private val repository: UserRepository
) {

    suspend operator fun invoke (
        request : RegisterRequestDomainModel
    ) : Result<Unit> {
        return runSuspendCatching {
            repository.register(request)
        }
    }

}