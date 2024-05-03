package com.example.task.presentation.search.searchRv

sealed interface SearchUiModel {

    data class Event(
        val id : Long,
        val title : String,
        val eventImage : String
    ) : SearchUiModel

    data object SearchBar: SearchUiModel

    data class User(
        val id : Long,
        val name : String,
        val userImage : String
    ) : SearchUiModel
}