package com.example.task.presentation.main

import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemTitleBinding

class ItemTitleViewHolder(
    private val binding : ItemTitleBinding,
    private val titleId : Int
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.rvTitleTv.text = binding.root.context.getString(titleId)
    }

    fun onBind() {
        binding.rvTitleTv.text = binding.root.context.getString(titleId)
    }
}