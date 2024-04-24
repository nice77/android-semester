package com.example.task.presentation.main.eventsRv

import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemEventsRecyclerBinding
import com.example.task.domain.models.EventDomainModel

class ItemEventsRecyclerVH(
    private val binding : ItemEventsRecyclerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind() {
        binding.eventsRv.adapter = EventsAdapter()
    }

    fun updateEventsList(eventsList: List<EventDomainModel>) {
        (binding.eventsRv.adapter as EventsAdapter).submitList(eventsList)
    }
}