package com.example.task.presentation.main.mainRv

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task.BuildConfig
import com.example.task.databinding.ItemEventBinding
import com.example.task.utils.loadCaching

class EventViewHolder(
    private val binding: ItemEventBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(uiModel: MainUiModel.Event) {
        binding.run {
            if (uiModel.model.eventImages.isNotEmpty()) {
                eventImg.loadCaching("${BuildConfig.PATH}${uiModel.model.eventImages.first()}")
            }
            eventTitleTv.text = uiModel.model.title
        }
    }
}
