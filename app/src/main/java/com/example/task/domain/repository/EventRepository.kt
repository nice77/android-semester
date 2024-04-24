package com.example.task.domain.repository

import com.example.task.domain.models.EventDomainModel

interface EventRepository {

    suspend fun getEvent(id : Long) : EventDomainModel

    suspend fun getEvents(page : Int) : List<EventDomainModel>

    suspend fun getEventImage(fileName : String) : ByteArray
}