package com.example.task.domain.usecases

import com.example.task.domain.repository.EventRepository
import com.example.task.utils.runSuspendCatching
import javax.inject.Inject

class GetEventImageUseCase @Inject constructor(
    private val eventRepository: EventRepository
) {

    suspend operator fun invoke(fileName : String) : Result<ByteArray> {
        return runSuspendCatching {
            eventRepository.getEventImage(fileName = fileName)
        }
    }

}