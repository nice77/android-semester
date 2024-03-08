package com.example.task.presentation.registration

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.task.R
import com.example.task.data.remote.datasource.NetworkManager
import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.databinding.FragmentRegisterBinding
import com.example.task.domain.validators.ValidateEmailUseCase
import com.example.task.domain.validators.ValidatePasswordUseCase
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch
import kotlinx.coroutines.newCoroutineContext
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.net.UnknownHostException
import kotlin.math.roundToInt

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private var binding : FragmentRegisterBinding?= null
    private var emailCorrect = false
    private var passwordCorrect = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding = FragmentRegisterBinding.bind(view)
        binding?.let { binding ->
            binding.emailEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val displayMetrics = resources.displayMetrics
                    val pixels = (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
                    val background = binding.emailEt.background as GradientDrawable

                    if (!ValidateEmailUseCase()(s.toString())) {
                        emailCorrect = false
                        background.setStroke(pixels, context!!.getColor(R.color.red))
                    }
                    else {
                        emailCorrect = true
                        val typedValue = TypedValue()
                        context!!.theme.resolveAttribute(com.google.android.material.R.attr.strokeColor, typedValue, true)
                        background.setStroke(pixels, typedValue.data)
                    }
                    checkInputCredentials(binding.submitBtn)
                }
            })

            binding.passwordEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

                override fun afterTextChanged(s: Editable?) {
                    val displayMetrics = resources.displayMetrics
                    val pixels = (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
                    val background = binding.passwordEt.background as GradientDrawable

                    if (!ValidatePasswordUseCase()(s.toString())) {
                        passwordCorrect = false
                        background.setStroke(pixels, context!!.getColor(R.color.red))
                    }
                    else {
                        passwordCorrect = true
                        val typedValue = TypedValue()
                        context!!.theme.resolveAttribute(com.google.android.material.R.attr.strokeColor, typedValue, true)
                        background.setStroke(pixels, typedValue.data)
                    }
                    checkInputCredentials(binding.submitBtn)
                }
            })

            binding.submitBtn.setOnClickListener {
                if (emailCorrect && passwordCorrect) {
                    findNavController().navigate(
                        R.id.action_registerFragment_to_authFragment,
                        Bundle().apply {
                            putBoolean(REGISTERED_KEY, true)
                        }
                    )
                }
            }

            binding.submitBtn.setOnClickListener {
                lifecycleScope.launch {
                    runCatching {
                        val credentials = RegisterRequest(
                            name = binding.nameEt.text.toString(),
                            email = binding.emailEt.text.toString(),
                            password = binding.passwordEt.text.toString()
                        )
                        println("TEST TAG: $credentials")
                        NetworkManager.getUserApi().register(request = credentials)
                    }.onSuccess {
                        findNavController().navigate(
                            R.id.action_registerFragment_to_authFragment,
                            Bundle().apply {
                                putBoolean(REGISTERED_KEY, true)
                            }
                        )
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

            binding.signInTv.setOnClickListener {
                findNavController().navigate(R.id.action_registerFragment_to_authFragment)
            }
        }
    }

    fun checkInputCredentials(button : View) {
        if (emailCorrect && passwordCorrect) {
            button.isEnabled = true
            return
        }
        button.isEnabled = false
    }

    companion object {
        private const val REGISTERED_KEY = "registered"
    }
}