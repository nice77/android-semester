package com.example.task.presentation.main

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task.BuildConfig
import com.example.task.databinding.ItemEventBinding
import com.example.task.domain.models.EventDomainModel

class EventViewHolder(
    private val binding : ItemEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    private var eventDomainModel : EventDomainModel? = null

    fun onBind(eventDomainModel: EventDomainModel) {
        this.eventDomainModel = eventDomainModel
        binding.run {
            if (eventDomainModel.eventImages.isNotEmpty()) {
                eventImg.load("${BuildConfig.PATH}${eventDomainModel.eventImages[0]}")
            }
            eventTitleTv.text = eventDomainModel.id.toString()
        }
    }
}