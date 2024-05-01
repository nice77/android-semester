package com.example.task.presentation.search

sealed interface SearchUiModel {

    data class Event(
        val id : Long,
        val title : String,
        val eventImages : List<String>
    ) : SearchUiModel

    data object SearchBar: SearchUiModel
}