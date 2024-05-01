package com.example.task.presentation.search

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task.BuildConfig
import com.example.task.databinding.ItemEventBinding

class EventViewHolder(
    private val binding : ItemEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item : SearchUiModel.Event) {
        if (item.eventImages.isNotEmpty()) {
            binding.eventImg.load(BuildConfig.PATH + item.eventImages[0])
        }
        binding.eventTitleTv.text = item.title
    }

}