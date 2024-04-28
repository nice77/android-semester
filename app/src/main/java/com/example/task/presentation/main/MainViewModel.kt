package com.example.task.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.task.domain.paging.EventPagingSource
import com.example.task.domain.paging.UserPagingSource
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn

class MainViewModel @AssistedInject constructor(
    private val eventPagingSource: EventPagingSource,
    private val userPagingSource: UserPagingSource
): ViewModel() {

    val eventsFlow = Pager(
        PagingConfig(
            pageSize = 10,
            prefetchDistance = 1
        )
    ) {
        eventPagingSource
    }.flow
        .stateIn(scope = viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val usersFlow = Pager(
        PagingConfig(
            pageSize = 10
        )
    ) {
        userPagingSource
    }.flow
        .stateIn(scope = viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @AssistedFactory
    interface Factory {
        fun create() : MainViewModel
    }
}