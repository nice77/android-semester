package com.example.task.presentation.search

import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemSearchBarBinding

class SearchBarViewHolder(
    private val binding : ItemSearchBarBinding,
    private val onTextUpdate: (String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.searchEt.addTextChangedListener { text ->
            onTextUpdate(text.toString())
        }
    }

}