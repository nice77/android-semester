package com.example.task.presentation.main.usersRv

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.task.BuildConfig
import com.example.task.databinding.ItemUserBinding
import com.example.task.domain.models.UserDomainModel
import com.example.task.utils.loadCaching

class UserViewHolder(
    private val binding : ItemUserBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(userDomainModel: UserDomainModel) {
        binding.run {
            if (userDomainModel.userImage.isNotEmpty()) {
                userImg.loadCaching("${BuildConfig.PATH}${userDomainModel.userImage}")
            }
            usernameTv.text = userDomainModel.name
        }
    }
}
