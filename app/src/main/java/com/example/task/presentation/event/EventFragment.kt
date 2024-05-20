package com.example.task.presentation.event

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.paging.PagingData
import androidx.paging.insertHeaderItem
import androidx.paging.map
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentEventBinding
import com.example.task.presentation.event.eventRv.EventAdapter
import com.example.task.presentation.event.eventRv.EventUiModel
import com.example.task.presentation.profile.ProfileFragment
import com.example.task.utils.component
import com.example.task.utils.lazyViewModel
import com.yandex.mapkit.MapKitFactory
import kotlinx.coroutines.launch

class EventFragment : Fragment(R.layout.fragment_event) {

    private val binding by viewBinding(FragmentEventBinding::bind)
    private val viewModel by lazyViewModel {
        requireContext().component.eventViewModel().create(arguments?.getLong(CURRENT_EVENT_KEY) ?: throw NoSuchElementException())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.eventRv.adapter = EventAdapter(
            getUser = ::getUser,
            isCurrentUser = ::isCurrentUser,
            onEventSubscribed = ::onEventSubscribed,
            amISubscribedToEvent = ::amISubscribedToEvent,
            sendComment = ::sendComment,
            onUserNameClicked = ::onUserNameClicked
        )
        observeData()
    }

    private fun getUser(userId : Long) {
        viewModel.getUser(userId = userId)
    }

    private fun isCurrentUser(userId: Long) {
        viewModel.getIsCurrentUser(userId = userId)
    }

    private fun onEventSubscribed() {
        viewModel.subscribeToEvent()
    }

    private fun amISubscribedToEvent() {
        viewModel.amISubscribedToEvent()
    }

    private fun sendComment(text : String) {
        viewModel.sendComment(text = text)
    }

    private fun onUserNameClicked(userId: Long) {
        val bundle = Bundle().apply {
            putLong(ProfileFragment.USER_ID_KEY, userId)
        }
        findNavController().navigate(R.id.action_eventFragment_to_profileFragment, bundle)
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                launch {
                    viewModel.eventPageFlow.collect {
                        (binding.eventRv.adapter as EventAdapter).submitData(it)
                    }
                }
                launch {
                    viewModel.userFlow.collect {
                        it?.let {
                            (binding.eventRv.adapter as EventAdapter).updateUserName(it.name)
                        }
                    }
                }
                launch {
                    viewModel.requestResultFlow.collect {
                        it?.let {
                            when (it.type) {
                                RequestResultTypeEnum.IS_CURRENT_USER -> (binding.eventRv.adapter as EventAdapter).showEditButton()
                                RequestResultTypeEnum.AM_I_SUBSCRIBED -> (binding.eventRv.adapter as EventAdapter).updateSubscribeButton(subbed = it.result)
                            }
                        }
                    }
                }
                launch {
                    viewModel.commentFlow.collect {
                        it?.let { comment ->
                            val adapter = (binding.eventRv.adapter as EventAdapter)
                            val currItems = adapter.snapshot().items.toMutableList()
                            currItems.add(2, comment)
                            adapter.submitData(PagingData.from(currItems))
                        }
                    }
                }
            }
        }
    }

    companion object {
        const val CURRENT_EVENT_KEY = "CURRENT_EVENT_KEY"
    }
}