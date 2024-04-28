package com.example.task.presentation.main.usersRv

import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.example.task.databinding.ItemUsersRecyclerBinding
import com.example.task.domain.models.UserDomainModel

class ItemUsersRecyclerVH(
    private val binding: ItemUsersRecyclerBinding
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.usersRv.adapter = UsersAdapter()
    }

    fun updateUsersList(lifecycle: Lifecycle, usersList : PagingData<UserDomainModel>) {
        (binding.usersRv.adapter as UsersAdapter).submitData(lifecycle = lifecycle, pagingData = usersList)
    }
}