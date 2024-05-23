package com.example.task.presentation.editevent.bottomsheet

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.ItemTouchHelper
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentEventImagesBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EventImagesBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_event_images) {

    private val binding by viewBinding(FragmentEventImagesBinding::bind)

    val sizeLeft = arguments?.getInt(IMAGE_SIZE_KEY) ?: 0
    private val loadedImages : MutableList<Uri> = mutableListOf()
    private val removedImages : MutableList<String> = mutableListOf()

    private val imageLauncher = registerForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxItems = 10 - sizeLeft)) {
        loadedImages.clear()
        loadedImages.addAll(it)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            val list = it.getStringArrayList(STRING_ARRAY_KEY) ?: throw NoSuchElementException()
            val bottomUiList : MutableList<BottomUiModel> = list.map(BottomUiModel::Image).toMutableList()
            repeat(2) {
                bottomUiList.add(BottomUiModel.Button)
            }
            binding.imagesRv.adapter = BottomSheetImagesAdapter(
                onAddButtonClicked = ::onAddButtonClicked,
                onSubmitButtonClicked = ::onSubmitButtonClicked,
                onItemSwiped = ::onItemSwiped
            )
            (binding.imagesRv.adapter as BottomSheetImagesAdapter).submitList(bottomUiList.toList())
            val callback = ItemSwipeHelper()
            val touchHelper = ItemTouchHelper(callback)
            touchHelper.attachToRecyclerView(binding.imagesRv)
        }
    }

    private fun onItemSwiped(path : String) {
        val adapter = (binding.imagesRv.adapter as BottomSheetImagesAdapter)
        adapter.submitList(adapter.currentList.filter { if (it is BottomUiModel.Image) it.path != path else true })
        removedImages.add(path)
    }

    private fun onAddButtonClicked() {
        imageLauncher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun onSubmitButtonClicked() {
        println("loadedImages: $loadedImages")
        println("upd: ${ArrayList(loadedImages.map {
            it.toString()
        })}")
        setFragmentResult(
            requestKey = FRAGMENT_RESULT_KEY,
            result = Bundle().apply {
                println("Adding prekls")
                putStringArrayList(IMAGES_AFTER_REMOVAL_KEY, ArrayList(removedImages))
                putStringArrayList(ARRAY_LIST_KEY, ArrayList(loadedImages.map {
                    it.toString()
                }))
            }
        )
        dismiss()
    }

    companion object {
        const val STRING_ARRAY_KEY = "STRING_ARRAY_KEY"
        const val IMAGE_SIZE_KEY = "IMAGE_SIZE_KEY"
        const val FRAGMENT_RESULT_KEY = "FRAGMENT_RESULT_KEY"
        const val ARRAY_LIST_KEY = "ARRAY_LIST_KEY"
        const val IMAGES_AFTER_REMOVAL_KEY = "IMAGES_AFTER_REMOVAL_KEY"

        fun getInstance(images : List<String>) : EventImagesBottomSheetFragment {
            val bundle = Bundle().apply {
                putStringArrayList(STRING_ARRAY_KEY, ArrayList(images))
            }
            return EventImagesBottomSheetFragment().apply {
                arguments = bundle
            }
        }
    }
}