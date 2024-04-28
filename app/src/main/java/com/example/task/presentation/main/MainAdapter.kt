package com.example.task.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.paging.PagingData
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.ItemEventBinding
import com.example.task.databinding.ItemTitleBinding
import com.example.task.databinding.ItemUsersRecyclerBinding
import com.example.task.domain.models.EventDomainModel
import com.example.task.domain.models.UserDomainModel
import com.example.task.presentation.main.usersRv.ItemUsersRecyclerVH
import java.lang.ref.WeakReference


class MainAdapter(
    private val lifecycle : WeakReference<Lifecycle>
) : PagingDataAdapter<Any, RecyclerView.ViewHolder>(ITEM_DIFF) {

    private var itemUsersRecyclerVH : ItemUsersRecyclerVH? = null
    private var usersList : PagingData<UserDomainModel>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            R.layout.item_users_recycler -> {
                val toReturn = ItemUsersRecyclerVH(
                    binding = ItemUsersRecyclerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                itemUsersRecyclerVH?.let {
                    return it
                }
                itemUsersRecyclerVH = toReturn
                usersList?.let { list ->
                    itemUsersRecyclerVH?.let { itemUsersRecyclerVH ->
                        lifecycle.get()?.let { lifecycle ->
                            itemUsersRecyclerVH.updateUsersList(lifecycle = lifecycle, list)
                        }
                    }
                }
                return toReturn
            }
            R.layout.item_event -> {
                return EventViewHolder(
                    binding = ItemEventBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
            }
            R.string.recommended_users -> ItemTitleViewHolder(
                binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            R.string.recommended_events -> ItemTitleViewHolder(
                binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw NoSuchElementException()
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            0 -> (holder as ItemTitleViewHolder).onBind(R.string.recommended_users)
            1 -> return
            2 -> (holder as ItemTitleViewHolder).onBind(R.string.recommended_events)
            else -> (holder as EventViewHolder).onBind(getItem(position) as EventDomainModel)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> R.string.recommended_users
            1 -> R.layout.item_users_recycler
            2 -> R.string.recommended_events
            else -> R.layout.item_event
        }
    }

    fun updateUsersList(lifecycle: Lifecycle, usersList : PagingData<UserDomainModel>) {
        itemUsersRecyclerVH?.let {
            it.updateUsersList(lifecycle, usersList)
            return
        }
        this.usersList = usersList
    }
    
    companion object {
        val ITEM_DIFF = object : DiffUtil.ItemCallback<Any>() {
            override fun areItemsTheSame(
                oldItem: Any,
                newItem: Any
            ): Boolean {
                if (oldItem is EventDomainModel && newItem is EventDomainModel) {
                    return oldItem.id == newItem.id
                }
                return true
            }

            override fun areContentsTheSame(
                oldItem: Any,
                newItem: Any
            ): Boolean {
                if (oldItem is EventDomainModel && newItem is EventDomainModel) {
                    return oldItem == newItem
                }
                return false
            }
        }
    }
}