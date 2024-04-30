package com.example.task.presentation.main.mainRv

import androidx.annotation.StringRes
import com.example.task.domain.models.EventDomainModel

sealed interface MainUiModel {
    // тут конечно надо бы не тупо доменную модельку хранить, а либо продублировать ее поля
    // либо как-то изменить и сделать так, чтобы остались только используемые
    data class Event(val model: EventDomainModel): MainUiModel
    data class Title(@StringRes val textRes: Int): MainUiModel
    data object Users: MainUiModel
}
