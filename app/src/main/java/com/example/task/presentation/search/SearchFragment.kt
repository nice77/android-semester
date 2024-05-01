package com.example.task.presentation.search

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentSearchBinding
import com.example.task.utils.component
import com.example.task.utils.lazyViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SearchFragment : Fragment(R.layout.fragment_search) {

    private val binding by viewBinding(FragmentSearchBinding::bind)
    private val viewModel : SearchViewModel by lazyViewModel {
        requireContext().component.searchViewModel().create()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchRv.adapter = SearchAdapter(::findByNameContaining)
        observeData()
    }

    private fun findByNameContaining(query : String) {
        viewModel.setupNewSearchQuery(query)
    }

    private fun observeData() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                viewModel.events.collectLatest {
                    (binding.searchRv.adapter as SearchAdapter).submitData(it)
                }
            }
        }
    }
}