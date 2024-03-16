package com.example.task.domain.validators

class ValidatePasswordUseCase {
    operator fun invoke(password : String) : Boolean {
        return password.matches(regex)
    }

    companion object {
        val regex = Regex("(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#\$%^&*]).{8,}\$")
    }
}