package com.example.task.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.ItemEventsRecyclerBinding
import com.example.task.databinding.ItemTitleBinding
import com.example.task.databinding.ItemUsersRecyclerBinding
import com.example.task.domain.models.EventDomainModel
import com.example.task.domain.models.UserDomainModel
import com.example.task.presentation.main.eventsRv.ItemEventsRecyclerVH
import com.example.task.presentation.main.usersRv.ItemUsersRecyclerVH


class MainAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var itemUsersRecyclerVH : ItemUsersRecyclerVH? = null
    private var itemEventsRecyclerVH : ItemEventsRecyclerVH? = null

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
                itemUsersRecyclerVH = toReturn
                return toReturn
            }
            R.layout.item_events_recycler -> {
                val toReturn = ItemEventsRecyclerVH(
                    binding = ItemEventsRecyclerBinding.inflate(
                        LayoutInflater.from(parent.context),
                        parent,
                        false
                    )
                )
                itemEventsRecyclerVH = toReturn
                return toReturn
            }
            R.string.recommended_users -> ItemTitleViewHolder(
                binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                titleId = R.string.recommended_users
            )
            R.string.recommended_events -> ItemTitleViewHolder(
                binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                titleId = R.string.recommended_events
            )
            else -> throw NoSuchElementException()
        }
    }

    override fun getItemCount(): Int = 4

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(position) {
            1 -> (holder as ItemUsersRecyclerVH).onBind()
            3 -> (holder as ItemEventsRecyclerVH).onBind()
            else -> (holder as ItemTitleViewHolder).onBind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            0 -> R.string.recommended_users
            1 -> R.layout.item_users_recycler
            2 -> R.string.recommended_events
            else -> R.layout.item_events_recycler
        }
    }

    fun updateUsersList(usersList : List<UserDomainModel>) {
        itemUsersRecyclerVH?.updateUsersList(usersList)
    }

    fun updateEventsList(eventsList: List<EventDomainModel>) {
        itemEventsRecyclerVH?.updateEventsList(eventsList)
    }
}