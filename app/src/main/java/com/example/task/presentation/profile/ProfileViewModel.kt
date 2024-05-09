package com.example.task.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.insertHeaderItem
import androidx.paging.map
import com.example.task.R
import com.example.task.data.repositories.paging.UserCreatedEventPagingSource
import com.example.task.data.repositories.paging.UserSubscribedEventPagingSource
import com.example.task.domain.models.EventDomainModel
import com.example.task.domain.models.UserDomainModel
import com.example.task.domain.usecases.GetUserUseCase
import com.example.task.presentation.profile.profileRv.ProfileUIModel
import com.example.task.presentation.search.searchRv.SearchUiModel
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ProfileViewModel @AssistedInject constructor(
    private val userCreatedEventPagingSourceFactory: UserCreatedEventPagingSource.Factory,
    private val userSubscribedEventPagingSourceFactory: UserSubscribedEventPagingSource.Factory,
    private val getUserUseCase: GetUserUseCase,
    @Assisted private val userId : Long?
) : ViewModel() {


    private val _checkedItem = MutableStateFlow(R.id.created_events_rb)
    val checkedItem : StateFlow<Int>
        get() = _checkedItem

    val eventList : StateFlow<PagingData<ProfileUIModel>> = _checkedItem
        .flatMapLatest { currCheckedItem ->
            println("currCheckedItem: $currCheckedItem")
            val pager = when (currCheckedItem) {
                R.id.created_events_rb -> createNewCreatedEventsPager()
                R.id.subscribed_events_rb -> createNewSubscribedEventsPager()
                else -> throw NoSuchMethodException()
            }
            pager
                .flow.map { pagingData ->
                    pagingData.map {
                        println("got: $it")
                        ProfileUIModel.Event(
                            id = it.id,
                            title = it.title,
                            eventImage = if (it.eventImages.isEmpty()) "" else it.eventImages[0]
                        )
                    } as PagingData<ProfileUIModel>
                }
                .map { pagingData ->
                    var newPagingData : PagingData<ProfileUIModel>
                    newPagingData = pagingData.insertHeaderItem(item = ProfileUIModel.Buttons)
                    val userResult = getUserUseCase(userId = userId)
                    userResult.onSuccess {
                        newPagingData = newPagingData.insertHeaderItem(item = ProfileUIModel.User(
                            id = it.id,
                            name = it.name,
                            age = it.age,
                            email = it.email,
                            city = it.city,
                            userImage = it.userImage,
                            subscribersCount = it.subscribersCount,
                            authorsCount = it.authorsCount
                        ))
                    }
                    newPagingData
                }
        }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.Lazily, PagingData.empty())

    private fun createNewCreatedEventsPager() : Pager<Int, EventDomainModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 1
            )
        ) {
            println("Returning pg1 (created)")
            userCreatedEventPagingSourceFactory.create(userId = userId)
        }
    }

    private fun createNewSubscribedEventsPager() : Pager<Int, EventDomainModel> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                initialLoadSize = 10,
                prefetchDistance = 1
            )
        ) {
            println("Returning pg2 (subbed)")
            userSubscribedEventPagingSourceFactory.create(userId = userId)
        }
    }

    fun emitNewCheckedItem(checkedItemId : Int) {
        viewModelScope.launch {
            _checkedItem.emit(checkedItemId)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(@Assisted userId: Long?) : ProfileViewModel
    }
}