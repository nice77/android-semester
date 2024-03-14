package com.example.task.data.local.sharedpreferences

import android.content.Context
import android.content.SharedPreferences
import com.example.task.utils.Keys

class Preferences (
    private val preferences : SharedPreferences
) {
    fun addTokens(entity: TokensEntity) {
        with (preferences.edit()) {
            putString(Keys.ACCESS_TOKEN, entity.access)
            putString(Keys.REFRESH_TOKEN, entity.refresh)
            commit()
        }
    }

    fun removeTokens() {
        with (preferences.edit()) {
            remove(Keys.ACCESS_TOKEN)
            remove(Keys.REFRESH_TOKEN)
            commit()
        }
    }

    fun getTokens() : TokensEntity? {
        val access = preferences.getString(Keys.ACCESS_TOKEN, null)
        val refresh = preferences.getString(Keys.REFRESH_TOKEN, null)
        if (access == null || refresh == null) {
            return null
        }
        return TokensEntity(
            access = access,
            refresh = refresh
        )
    }
}