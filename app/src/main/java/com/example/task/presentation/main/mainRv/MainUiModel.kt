package com.example.task.presentation.main.mainRv

import androidx.annotation.StringRes
import com.example.task.domain.models.EventDomainModel

sealed interface MainUiModel {
    data class Event(
        val id : Long,
        val title : String,
        val eventImages : List<String>
    ): MainUiModel
    data class Title(@StringRes val textRes: Int): MainUiModel
    data object Users: MainUiModel
}
