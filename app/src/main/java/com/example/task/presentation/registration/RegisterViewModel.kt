package com.example.task.presentation.registration

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.util.DisplayMetrics
import android.util.TypedValue
import android.widget.EditText
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.task.R
import com.example.task.data.remote.datasource.requests.RegisterRequest
import com.example.task.domain.usecases.RegisterUserUseCase
import com.example.task.domain.validators.ValidateEmailUseCase
import com.example.task.domain.validators.ValidatePasswordUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class RegisterViewModel(
    private val registerUserUseCase: RegisterUserUseCase
) : ViewModel() {

    private val _emailCorrect = MutableSharedFlow<Boolean>()
    private val _passwordCorrect = MutableSharedFlow<Boolean>()
    private val _combinedFlow = combine (
        _emailCorrect,
        _passwordCorrect
    ) { first, second ->
        first && second
    }
    val combinedFlow : Flow<Boolean>
        get() = _combinedFlow


    private val _errorFlow = MutableSharedFlow<Throwable>()
    val errorFlow : SharedFlow<Throwable>
        get() = _errorFlow


    fun registerUser(name : String, email : String, password : String) {
        viewModelScope.launch {
            val request = RegisterRequest(
                name = name,
                email = email,
                password = password
            )
            registerUserUseCase.register(request, _errorFlow)
        }
    }

    fun validateEmail(ctx : Context, text : String, editText: EditText) {
        viewModelScope.launch {
            _emailCorrect.emit(checkInputValues(ctx, text, editText, ValidateEmailUseCase()::invoke))
        }
    }

    fun validatePassword(ctx : Context, text : String, editText: EditText) {
        viewModelScope.launch {
            _passwordCorrect.emit(checkInputValues(ctx, text, editText, ValidatePasswordUseCase()::invoke))
        }
    }

    private fun checkInputValues(ctx : Context,
                                 text : String,
                                 editText: EditText,
                                 predicate : (String) -> Boolean) : Boolean {
        val pixels = (ctx.resources.displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT).roundToInt()
        val background = editText.background as GradientDrawable

        if (!predicate(text)) {
            background.setStroke(pixels, ctx.getColor(R.color.red))
            return false
        }
        val typedValue = TypedValue()
        ctx.theme.resolveAttribute(com.google.android.material.R.attr.strokeColor, typedValue, true)
        background.setStroke(pixels, typedValue.data)
        return true
    }

    companion object {
        val factory = viewModelFactory {
            initializer {
                val registerUserUseCase = RegisterUserUseCase()
                RegisterViewModel(registerUserUseCase)
            }
        }
    }
}