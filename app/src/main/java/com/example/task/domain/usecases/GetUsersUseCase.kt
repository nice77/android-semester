package com.example.task.domain.usecases

import com.example.task.data.repositories.UserRepositoryImpl
import com.example.task.domain.models.UserDomainModel
import com.example.task.utils.runSuspendCatching
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(
    private val userRepositoryImpl: UserRepositoryImpl
) {

    suspend operator fun invoke(
        page : Int
    ) : Result<List<UserDomainModel>> {
        return runSuspendCatching {
            userRepositoryImpl.getUsers(page)
        }
    }

}