package com.example.task.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.task.domain.models.EventDomainModel
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.usecases.GetEventsUseCase
import com.example.task.domain.usecases.GetUsersUseCase
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MainViewModel @AssistedInject constructor(
    private val getEventsUseCase: GetEventsUseCase,
    private val getUsersUseCase: GetUsersUseCase
): ViewModel() {

    private val _eventsFlow = MutableStateFlow<List<EventDomainModel>>(listOf())
    val eventsFlow : StateFlow<List<EventDomainModel>>
        get() = _eventsFlow

    private val _usersFlow = MutableStateFlow<List<UserDomainModel>>(listOf())
    val usersFlow : StateFlow<List<UserDomainModel>>
        get() = _usersFlow

    @AssistedFactory
    interface Factory {
        fun create() : MainViewModel
    }

    fun getUsers(page : Int) {
        viewModelScope.launch {
            getUsersUseCase(page = page).onSuccess {
                _usersFlow.emit(it)
            }
        }
    }

    fun getEvents(page : Int) {
        viewModelScope.launch {
            getEventsUseCase(page = page).onSuccess {
                _eventsFlow.emit(it)
            }
        }
    }

}