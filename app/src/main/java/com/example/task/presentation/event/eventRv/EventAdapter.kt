package com.example.task.presentation.event.eventRv

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.task.R
import com.example.task.databinding.ItemCommentBinding
import com.example.task.databinding.ItemCommentInputFieldBinding
import com.example.task.databinding.ItemCurrentEventBinding

class EventAdapter(
    private val getUser: (Long) -> Unit,
    private val isCurrentUser: (Long) -> Unit,
    private val onEventSubscribed: () -> Unit,
    private val amISubscribedToEvent: () -> Unit,
    private val sendComment: (String) -> Unit,
    private val onUserNameClicked: (Long) -> Unit
) : PagingDataAdapter<EventUiModel, RecyclerView.ViewHolder>(ITEM_DIFF) {

    private var eventViewHolder : EventViewHolder? = null
    private var isEventAuthorNameLoaded : Boolean = false

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is EventUiModel.Event -> {
                (holder as EventViewHolder).onBind(item)
                if (!isEventAuthorNameLoaded) {
                    getUser(item.authorId)
                    isCurrentUser(item.authorId)
                    amISubscribedToEvent()
                }
            }
            is EventUiModel.CommentInputField -> Unit
            is EventUiModel.Comment -> (holder as CommentViewHolder).onBind(item)
            else -> throw NoSuchElementException()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            R.layout.item_current_event -> {
                eventViewHolder = EventViewHolder(
                    binding = ItemCurrentEventBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                    onEventSubscribed = onEventSubscribed,
                    onUserNameClicked = onUserNameClicked
                )
                eventViewHolder!!
            }
            R.layout.item_comment_input_field -> InputFieldViewHolder(
                binding = ItemCommentInputFieldBinding.inflate(LayoutInflater.from(parent.context), parent, false),
                sendComment = sendComment
            )
            R.layout.item_comment -> CommentViewHolder(
                binding = ItemCommentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            )
            else -> throw NoSuchElementException()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> R.layout.item_current_event
            1 -> R.layout.item_comment_input_field
            else -> R.layout.item_comment
        }
    }

    fun updateUserName(userName : String) {
        isEventAuthorNameLoaded = true
        eventViewHolder?.updateUserName(userName = userName)
    }

    fun showEditButton() {
        eventViewHolder?.showEditButton()
    }

    fun updateSubscribeButton(subbed : Boolean) {
        eventViewHolder?.updateSubscribeButton(subbed)
    }

    companion object {
        private val ITEM_DIFF = object : DiffUtil.ItemCallback<EventUiModel>() {
            override fun areItemsTheSame(oldItem: EventUiModel, newItem: EventUiModel): Boolean {
                return when {
                    oldItem is EventUiModel.Event && newItem is EventUiModel.Event -> oldItem.id == newItem.id
                    oldItem is EventUiModel.Comment && newItem is EventUiModel.Comment -> oldItem.id == newItem.id
                    else -> false
                }
            }

            override fun areContentsTheSame(oldItem: EventUiModel, newItem: EventUiModel): Boolean {
                return oldItem == newItem
            }

        }
    }
}