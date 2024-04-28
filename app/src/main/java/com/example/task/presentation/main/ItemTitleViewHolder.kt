package com.example.task.presentation.main

import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemTitleBinding

class ItemTitleViewHolder(
    private val binding : ItemTitleBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(titleId : Int) {
        binding.rvTitleTv.text = binding.root.context.getString(titleId)
    }
}