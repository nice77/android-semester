package com.example.task.presentation.search

import android.os.Bundle
import android.view.View
import androidx.annotation.IdRes
import androidx.core.view.get
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentFilterBottomBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class FilterBottomFragment(
    @IdRes private val checkedId : Int,
    private val onCheck: (Int) -> Unit
) : BottomSheetDialogFragment(R.layout.fragment_filter_bottom) {

    private val binding by viewBinding(FragmentFilterBottomBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.radioGroup.check(checkedId)
        binding.radioGroup.setOnCheckedChangeListener { radioGroup, i ->
            onCheck(i)
            dismiss()
        }
    }

    companion object {
        const val FILTER_BOTTOM_FRAGMENT_TAG = "FILTER_BOTTOM_FRAGMENT"
    }
}