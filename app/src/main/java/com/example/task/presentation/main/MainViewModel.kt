package com.example.task.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.example.task.R
import com.example.task.data.repositories.paging.EventPagingSource
import com.example.task.data.repositories.paging.UserPagingSource
import com.example.task.presentation.main.mainRv.MainUiModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class MainViewModel @AssistedInject constructor(
    private val eventPagingSource: EventPagingSource,
    private val userPagingSource: UserPagingSource
) : ViewModel() {

    val eventsFlow = Pager(
        PagingConfig(
            initialLoadSize = 10,
            pageSize = 10,
            prefetchDistance = 1
        )
    ) {
        eventPagingSource
    }.flow
        .map { pagingData ->
            pagingData.map {
                MainUiModel.Event(
                    id = it.id,
                    title = it.title,
                    eventImages = it.eventImages
                )
            } as PagingData<MainUiModel>
        }
        .map {
            it.insertHeaderItem(item = MainUiModel.Title(R.string.recommended_users))
                .insertHeaderItem(item = MainUiModel.Users)
                .insertHeaderItem(item = MainUiModel.Title(R.string.recommended_events))
        }
        .cachedIn(viewModelScope)
        .stateIn(scope = viewModelScope, SharingStarted.Lazily, PagingData.empty())

    val usersFlow = Pager(
        PagingConfig(
            initialLoadSize = 10,
            pageSize = 10,
            prefetchDistance = 1
        )
    ) {
        userPagingSource
    }.flow
        .cachedIn(viewModelScope)
        .stateIn(scope = viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @AssistedFactory
    interface Factory {
        fun create(): MainViewModel
    }
}
