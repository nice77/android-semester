package com.example.task.presentation.main

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.map
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentMainBinding
import com.example.task.utils.component
import com.example.task.utils.lazyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.lang.ref.WeakReference

class MainFragment : Fragment(R.layout.fragment_main) {

    private val binding : FragmentMainBinding by viewBinding(FragmentMainBinding::bind)
    private val viewModel : MainViewModel by lazyViewModel {
        requireContext().component.mainViewModel().create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mainRv.adapter = MainAdapter(WeakReference(lifecycle))
        observeData()
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                with(viewModel) {
                    launch {
                        usersFlow.collectLatest {
                            (binding.mainRv.adapter as MainAdapter).updateUsersList(lifecycle, it)
                        }
                    }
                    eventsFlow.collectLatest {
                        (binding.mainRv.adapter as MainAdapter).submitData(it.map { it })
                    }
                }
            }
        }
    }
}