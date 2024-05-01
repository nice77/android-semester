package com.example.task.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.ItemEventBinding
import com.example.task.databinding.ItemSearchBarBinding

class SearchAdapter(
    private val onTextUpdate: (String) -> Unit
) : PagingDataAdapter<SearchUiModel, RecyclerView.ViewHolder>(ITEM_DIFF) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is SearchUiModel.Event -> (holder as EventViewHolder).onBind(item)
            else -> Unit
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_search_bar -> SearchBarViewHolder(
                binding = ItemSearchBarBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                onTextUpdate = onTextUpdate
            )
            R.layout.item_event -> EventViewHolder(
                binding = ItemEventBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw NoSuchElementException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_search_bar
            else -> R.layout.item_event
        }
    }

    companion object {
        val ITEM_DIFF = object : DiffUtil.ItemCallback<SearchUiModel>() {
            override fun areItemsTheSame(oldItem: SearchUiModel, newItem: SearchUiModel): Boolean {
                return when {
                    oldItem is SearchUiModel.Event && newItem is SearchUiModel.Event -> oldItem.id == newItem.id
                    else -> false
                }
            }

            override fun areContentsTheSame(
                oldItem: SearchUiModel,
                newItem: SearchUiModel
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}