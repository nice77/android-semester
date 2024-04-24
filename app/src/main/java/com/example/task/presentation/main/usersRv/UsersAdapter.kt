package com.example.task.presentation.main.usersRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.task.databinding.ItemUserBinding
import com.example.task.domain.models.UserDomainModel

class UsersAdapter : ListAdapter<UserDomainModel, UserViewHolder>(ITEM_DIFF) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    companion object {
        val ITEM_DIFF : DiffUtil.ItemCallback<UserDomainModel> = object : DiffUtil.ItemCallback<UserDomainModel>() {
            override fun areItemsTheSame(oldItem: UserDomainModel, newItem: UserDomainModel): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: UserDomainModel,
                newItem: UserDomainModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}