package com.example.task.presentation.welcome

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.databinding.FragmentWelcomeBinding

class WelcomeFragment : Fragment(R.layout.fragment_welcome) {

    private var binding : FragmentWelcomeBinding ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentWelcomeBinding.bind(view).also { binding ->
            binding.signInBtn.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_authFragment)
            }
            binding.registerBtn.setOnClickListener {
                findNavController().navigate(R.id.action_welcomeFragment_to_registerFragment)
            }
        }
    }
}