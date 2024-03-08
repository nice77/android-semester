package com.example.task.presentation.authentication

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.AuthenticationRequest
import com.example.task.databinding.FragmentAuthBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.net.UnknownHostException

class AuthFragment : Fragment(R.layout.fragment_auth) {

    private var binding : FragmentAuthBinding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAuthBinding.bind(view)
        arguments?.let {
            if (it.getBoolean(REGISTERED_KEY)) {
                Snackbar.make(view, getString(R.string.register_success), Snackbar.LENGTH_SHORT).show()
            }
        }
        binding?.let { binding ->
            binding.registerTv.setOnClickListener {
                findNavController().navigate(R.id.action_authFragment_to_registerFragment)
            }
            binding.submitBtn.setOnClickListener {
                val request = AuthenticationRequest(
                    email = binding.emailEt.text.toString(),
                    password = binding.passwordEt.text.toString()
                )

                lifecycleScope.launch {
                    runCatching {
                        NetworkManager.getAuthApi().authenticate(request)
                    }.onSuccess {
                        println(it)
                    }.onFailure { exception ->
                        when (exception) {
                            is HttpException -> {
                                val code = exception.response()!!.code()
                                val msg = exception.response()!!.message()
                                Snackbar.make(view, "$code: $msg", Snackbar.LENGTH_LONG).show()
                            }
                            is UnknownHostException -> {
                                Snackbar.make(view, getString(R.string.unknown_host), Snackbar.LENGTH_LONG).show()
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