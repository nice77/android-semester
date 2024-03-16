package com.example.task.presentation.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.AuthenticationRequest
import com.example.task.databinding.FragmentAuthBinding
import com.example.task.domain.models.ErrorEnum
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private val binding : FragmentAuthBinding by viewBinding(FragmentAuthBinding::bind)

    private val viewModel : AuthViewModel by viewModels { AuthViewModel.factory }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeData()

        binding.run {
            registerTv.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_registerFragment)
            }
            submitBtn.setOnClickListener {
                viewModel.authenticateUser(
                    email = binding.emailEt.text.toString(),
                    password = binding.passwordEt.text.toString()
                )
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch(Dispatchers.IO) {
            with (viewModel) {

                launch {
                    errorFlow.collect { error ->
                        when (error) {
                            ErrorEnum.UNKNOWN_HOST -> showSnackbar(getString(R.string.unknown_host))
                            ErrorEnum.EMAIL_IN_USE -> {}
                        }
                    }
                }

            }
        }
    }

    private fun showSnackbar(message : String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_LONG).show()
    }
}