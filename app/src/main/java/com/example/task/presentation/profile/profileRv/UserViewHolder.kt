package com.example.task.presentation.profile.profileRv

import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.task.BuildConfig
import com.example.task.R
import com.example.task.databinding.ItemUserCardBinding
import com.example.task.utils.loadCaching

class UserViewHolder(
    private val binding : ItemUserCardBinding,
    private val onEditButtonPressed: () -> Unit,
    private val manageSubscriptionToUser: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var subState = false

    init {
        binding.editIv.setOnClickListener {
            onEditButtonPressed()
        }
        binding.subsribeBtn.setOnClickListener {
            manageSubscriptionToUser()
            updateSubscriptionButton(!subState)
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
            createNewEventBtn.isVisible = uiModel.isCurrentUser
            subsribeBtn.isVisible = !uiModel.isCurrentUser
        }
    }

    fun updateSubscriptionButton(subState : Boolean) {
        this.subState = subState
        val subscribedText = binding.root.context.getText(R.string.subscribe)
        val removeText = binding.root.context.getText(R.string.unsub_from_user)
        binding.subsribeBtn.text = if (subState) removeText else subscribedText
    }


}