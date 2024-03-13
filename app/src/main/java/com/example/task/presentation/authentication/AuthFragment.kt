package com.example.task.presentation.authentication

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.task.R
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.AuthenticationRequest
import com.example.task.databinding.FragmentAuthBinding
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
        arguments?.let {
            if (it.getBoolean(REGISTERED_KEY)) {
                Snackbar.make(view, getString(R.string.register_success), Snackbar.LENGTH_SHORT).show()
            }
        }

        binding.run {
            registerTv.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_registerFragment)
            }
            submitBtn.setOnClickListener {
                viewModel.authenticateUser(
                    email = binding.emailEt.text.toString(),
                    password = binding.emailEt.text.toString()
                )
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

            }
        }
    }

    companion object {
        private const val REGISTERED_KEY = "registered"
    }
}