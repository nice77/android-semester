package com.example.task.presentation.main.usersRv

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task.BuildConfig
import com.example.task.databinding.ItemUserBinding
import com.example.task.domain.models.UserDomainModel

class UserViewHolder(
    private val binding : ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(userDomainModel: UserDomainModel) {
        binding.run {
            if (userDomainModel.userImage.isNotEmpty()) {
                userImg.load("${BuildConfig.PATH}${userDomainModel.userImage}")
            }
            usernameTv.text = userDomainModel.name
        }
    }
}