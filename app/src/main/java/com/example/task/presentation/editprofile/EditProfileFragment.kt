package com.example.task.presentation.editprofile

import android.os.Bundle
import android.view.View
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.BuildConfig
import com.example.task.R
import com.example.task.databinding.FragmentEditProfileBinding
import com.example.task.domain.models.UserDomainModel
import com.example.task.utils.component
import com.example.task.utils.lazyViewModel
import com.example.task.utils.loadCaching
import kotlinx.coroutines.launch

class EditProfileFragment : Fragment(R.layout.fragment_edit_profile) {

    private val viewModel : EditProfileViewModel by lazyViewModel {
        requireContext().component.editProfileViewModel().create()
    }
    private val binding by viewBinding(FragmentEditProfileBinding::bind)

    private var userDomainModel : UserDomainModel? = null

    private val launcher = registerForActivityResult(ActivityResultContracts.PickVisualMedia()) {
        it?.let { uri ->
            viewModel.updateUserImage(requireContext().contentResolver.openInputStream(uri))
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()
        viewModel.getUserById()
        binding.run {
            avatarIv.setOnClickListener {
                onImageClicked()
            }
            nameEt.addTextChangedListener {
                userDomainModel = userDomainModel?.copy(
                    name = it.toString()
                )
            }
            ageEt.addTextChangedListener {
                userDomainModel = userDomainModel?.copy(
                    age = it.toString().toInt()
                )
            }
            cityEt.addTextChangedListener {
                userDomainModel = userDomainModel?.copy(
                    city = it.toString()
                )
            }
            submitBtn.setOnClickListener {
                onSubmitButtonClicked()
            }
        }
    }

    private fun onImageClicked() {
        launcher.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private fun onSubmitButtonClicked() {
        binding.submitBtn.isEnabled = false
        userDomainModel?.let {
            viewModel.onSubmitButtonClicked(userDomainModel = it)
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            repeatOnLifecycle(state = Lifecycle.State.STARTED) {
                launch {
                    viewModel.submitFlow.collect {
                        if (it) {
                            findNavController().navigate(R.id.action_editProfileFragment_to_profileFragment)
                        } else {
                            binding.submitBtn.isEnabled = true
                        }
                    }
                }
                launch {
                    viewModel.imageNameFlow.collect {
                        it?.let {
                            userDomainModel = userDomainModel?.copy(
                                userImage = it
                            )
                        }
                    }
                }
                launch {
                    viewModel.userFlow.collect {
                        it?.let { userDomainModel ->
                            this@EditProfileFragment.userDomainModel = userDomainModel
                            binding.run {
                                avatarIv.loadCaching(BuildConfig.PATH + userDomainModel.userImage)
                                nameEt.append(userDomainModel.name)
                                ageEt.append(userDomainModel.age.toString())
                                cityEt.append(userDomainModel.city)
                                submitBtn.isEnabled = true
                            }
                        }
                    }
                }
            }
        }
    }
}