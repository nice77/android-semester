package com.example.task.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentMainBinding
import com.example.task.utils.component
import com.example.task.utils.lazyViewModel
import kotlinx.coroutines.launch

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding : FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel : MainViewModel by lazyViewModel {
        requireContext().component.mainViewModel().create()
    }
    private var usersPage = 0
    private var eventsPage = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        binding.mainRv.adapter = MainAdapter()
        getEvents()
        getUsers()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                with(viewModel) {
                    launch {
                        usersFlow.collect {
                            (binding.mainRv.adapter as MainAdapter).updateUsersList(it)
                        }
                    }
                    launch {
                        eventsFlow.collect {
                            (binding.mainRv.adapter as MainAdapter).updateEventsList(it)
                        }
                    }
                }
            }
        }
    }

    private fun getEvents() {
        viewModel.getEvents(eventsPage)
    }

    private fun getUsers() {
        viewModel.getUsers(usersPage)
    }
}