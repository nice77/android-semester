package com.example.task.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.example.task.data.repositories.paging.EventByNamePagingSource
import com.example.task.domain.models.EventDomainModel
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class SearchViewModel @AssistedInject constructor(
    private val eventByNamePagingSourceFactory: EventByNamePagingSource.Factory
): ViewModel() {

    private val _query = MutableStateFlow<String?>(null)
    val query : StateFlow<String?>
        get() = _query


    val events : StateFlow<PagingData<SearchUiModel>> = query
        .map { queryString ->
            createNewPager(queryString ?: "")
        }
        .flatMapLatest { pager ->
            pager
                .flow
                .map { pagingData ->
                    pagingData.map {
                        SearchUiModel.Event(
                            id = it.id,
                            title = it.title,
                            eventImages = it.eventImages
                        )
                    } as PagingData<SearchUiModel>
                }
                .map {
                    it.insertHeaderItem(item = SearchUiModel.SearchBar)
                }
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    @AssistedFactory
    interface Factory {
        fun create() : SearchViewModel
    }

    fun setupNewSearchQuery(query : String) {
        viewModelScope.launch {
            _query.emit(query)
        }
    }

    private fun createNewPager(query : String) : Pager<Int, EventDomainModel> {
        return Pager(
            PagingConfig(
                pageSize = 10
            )
        ) {
            eventByNamePagingSourceFactory.create(query)
        }
    }
}