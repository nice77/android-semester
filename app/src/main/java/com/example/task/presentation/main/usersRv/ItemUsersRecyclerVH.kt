package com.example.task.presentation.main.usersRv

import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemUsersRecyclerBinding
import com.example.task.domain.models.UserDomainModel

class ItemUsersRecyclerVH(
    private val binding: ItemUsersRecyclerBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind() {
        binding.usersRv.adapter = UsersAdapter()
    }

    fun updateUsersList(usersList : List<UserDomainModel>) {
        (binding.usersRv.adapter as UsersAdapter).submitList(usersList)
    }
}