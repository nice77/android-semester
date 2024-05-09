package com.example.task.domain.repository

import com.example.task.domain.models.EventDomainModel
import com.example.task.domain.models.TokensDomainModel
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.models.request.RegisterRequestDomainModel

interface UserRepository {

    suspend fun register(request: RegisterRequestDomainModel) : TokensDomainModel

    suspend fun getUsers(page : Int = 0) : List<UserDomainModel>

    suspend fun getUsersByName(page : Int, name : String) : List<UserDomainModel>

    suspend fun getUsersSubscribedEvents(userId : Long?, page : Int) : List<EventDomainModel>

    suspend fun getUsersCreatedEvents(userId : Long?, page : Int) : List<EventDomainModel>

    suspend fun getCurrentUserId() : Long

    suspend fun getUser(userId : Long?) : UserDomainModel
}