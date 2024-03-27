package com.example.task.domain.usecases

import com.example.task.data.repositories.UserRepository
import com.example.task.domain.models.UserDomainModel
import com.example.task.utils.runSuspendCatching

class GetUsersUseCase(
    private val userRepository: UserRepository
) {

    suspend operator fun invoke(
        accessToken : String,
        page : Int
    ) : Result<List<UserDomainModel>> {
        return runSuspendCatching {
            userRepository.getUsers(accessToken, page)
        }
    }

}