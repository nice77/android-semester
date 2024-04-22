package com.example.task.domain.usecases

import com.example.task.data.repositories.UserRepositoryImpl
import com.example.task.domain.models.request.RegisterRequestDomainModel
import com.example.task.utils.runSuspendCatching
import javax.inject.Inject

class RegisterUserUseCase @Inject constructor (
    private val repository: UserRepositoryImpl
) {

    suspend operator fun invoke (
        request : RegisterRequestDomainModel
    ) : Result<Unit> {
        return runSuspendCatching {
            repository.register(request)
        }
    }

}