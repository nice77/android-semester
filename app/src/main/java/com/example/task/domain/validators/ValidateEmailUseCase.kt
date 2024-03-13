package com.example.task.domain.validators

import android.util.Patterns

class ValidateEmailUseCase {
    operator fun invoke(email : String) : Boolean {
        return email.matches(Regex(Patterns.EMAIL_ADDRESS.pattern()))
    }
}