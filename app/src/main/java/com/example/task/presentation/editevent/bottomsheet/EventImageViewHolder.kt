package com.example.task.presentation.editevent.bottomsheet

import androidx.recyclerview.widget.RecyclerView
import com.example.task.BuildConfig
import com.example.task.databinding.ItemBottomSheetImageBinding
import com.example.task.utils.loadCaching

class EventImageViewHolder(
    private val binding : ItemBottomSheetImageBinding,
    private val onItemSwiped: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var path : String? = null

    fun onBind(image : BottomUiModel.Image) {
        path = image.path
        binding.eventImg.loadCaching(BuildConfig.PATH + image.path)
    }

    fun remove() {
        path?.let(onItemSwiped)
    }
}