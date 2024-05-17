package com.example.task.presentation.profile.profileRv

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.task.BuildConfig
import com.example.task.databinding.ItemUserCardBinding
import com.example.task.utils.loadCaching

class UserViewHolder(
    private val binding : ItemUserCardBinding,
    private val onEditButtonPressed: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.editIv.setOnClickListener {
            onEditButtonPressed()
        }
    }

    fun onBind(uiModel: ProfileUIModel.User) {
        binding.run {
            avatarIv.loadCaching(BuildConfig.PATH + uiModel.userImage)
            userGmailTv.text = uiModel.email
            userNameTv.text = uiModel.name
            authorsCountTv.text = uiModel.authorsCount.toString()
            followersCountTv.text = uiModel.subscribersCount.toString()
            editIv.isVisible = uiModel.isCurrentUser
        }
    }

}