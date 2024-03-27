package com.example.task.data.repositories

import com.example.task.data.local.sharedpreferences.TokensEntity
import com.example.task.data.local.sharedpreferences.TokensPreferences
import com.example.task.domain.models.TokensDomainModel

class TokensRepository (
    private val preferences: TokensPreferences
) {

    fun addTokens(tokens : TokensDomainModel) {
        preferences.addTokens(TokensEntity(access = tokens.access, refresh = tokens.refresh))
    }

    fun removeTokens() {
        preferences.removeTokens()
    }

    fun getTokens() : TokensDomainModel? {
        val tokens = preferences.getTokens()
        tokens?.let {
            return TokensDomainModel(access = it.access, refresh = it.refresh)
        }
        return null
    }

    fun updateTokens(tokens : TokensDomainModel) {
        preferences.updateTokens(TokensEntity(access = tokens.access, refresh = tokens.refresh))
    }

}