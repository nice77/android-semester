package com.example.task.domain.validators

class ValidateEmailUseCase {
    operator fun invoke(email : String) : Boolean {
        return email.matches(Regex("^\\S+@\\S+\\.\\S+\$"))
    }
}