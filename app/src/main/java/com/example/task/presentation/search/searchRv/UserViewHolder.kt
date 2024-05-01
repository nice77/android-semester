package com.example.task.presentation.search.searchRv

import androidx.recyclerview.widget.RecyclerView
import com.example.task.BuildConfig
import com.example.task.databinding.ItemHorizontalUserBinding
import com.example.task.utils.loadCaching

class UserViewHolder(
    private val binding : ItemHorizontalUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(item : SearchUiModel.User) {
        binding.run {
            userImageIv.loadCaching(BuildConfig.PATH + item.userImage)
            nameTv.text = item.name
        }
    }

}