package com.example.task.presentation.main.eventsRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.task.databinding.ItemEventBinding
import com.example.task.domain.models.EventDomainModel
import com.example.task.utils.component

class EventsAdapter : ListAdapter<EventDomainModel, EventViewHolder>(ITEM_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        return EventViewHolder(
            binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        val ITEM_DIFF = object : DiffUtil.ItemCallback<EventDomainModel>() {
            override fun areItemsTheSame(
                oldItem: EventDomainModel,
                newItem: EventDomainModel
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: EventDomainModel,
                newItem: EventDomainModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}