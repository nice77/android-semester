package com.example.task.data.repositories

import com.example.task.data.mapper.ToDomainModelMapper
import com.example.task.data.remote.datasource.EventApi
import com.example.task.domain.models.EventDomainModel
import com.example.task.domain.repository.EventRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class EventRepositoryImpl @Inject constructor(
    private val toDomainModelMapper: ToDomainModelMapper,
    private val eventApi: EventApi
) : EventRepository {
    override suspend fun getEvent(id: Long): EventDomainModel {
        return toDomainModelMapper.mapEventResponseToEventDomainModel(eventApi.getEvent(id = id))
    }

    override suspend fun getEvents(page: Int): List<EventDomainModel> {
        return eventApi.getEvents(page = page).map(toDomainModelMapper::mapEventResponseToEventDomainModel)
    }

    override suspend fun getEventsByTitle(page: Int, title: String): List<EventDomainModel> {
        return eventApi.getEventsByName(
            name = title,
            page = page
        ).map(toDomainModelMapper::mapEventResponseToEventDomainModel)
    }
}