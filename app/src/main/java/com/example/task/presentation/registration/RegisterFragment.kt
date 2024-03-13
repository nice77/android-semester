package com.example.task.presentation.registration

import android.os.Bundle
import android.view.View
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.databinding.FragmentRegisterBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val binding : FragmentRegisterBinding by viewBinding(FragmentRegisterBinding::bind)
    private val viewModel : RegisterViewModel by viewModels { RegisterViewModel.factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        observeData()

        binding.run {
            emailEt.addTextChangedListener { text ->
                viewModel.validateEmail(requireContext(), text.toString(), binding.emailEt)
            }

            passwordEt.addTextChangedListener { text ->
                viewModel.validatePassword(requireContext(), text.toString(), binding.passwordEt)
            }

            submitBtn.setOnClickListener {
                viewModel.registerUser(
                    name = binding.nameEt.text.toString(),
                    email = binding.emailEt.text.toString(),
                    password = binding.passwordEt.text.toString()
                )
            }

            signInTv.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_authFragment)
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch(Dispatchers.IO) {
            with (viewModel) {
                launch {
                    errorFlow.collect { exception ->
                        when (exception) {
                            is HttpException -> {
                                val code = exception.response()!!.code()
                                val msg = exception.response()!!.message()
                                Snackbar.make(requireView(), "$code: $msg", Snackbar.LENGTH_LONG).show()
                            }
                            is UnknownHostException -> {
                                Snackbar.make(requireView(), getString(R.string.unknown_host), Snackbar.LENGTH_LONG).show()
                            }
                        }
                    }
                }
                launch(Dispatchers.Main) {
                    combinedFlow.collect { result ->
                        binding.submitBtn.isEnabled = result
                    }
                }
            }
        }
    }

    companion object {
        private const val REGISTERED_KEY = "registered"
    }
}