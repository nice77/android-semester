package com.example.task.data.local.sharedpreferences

import android.content.Context
import com.example.task.utils.Keys

class Preferences (
    private val ctx : Context
) {
    fun addTokens(entity: TokensEntity) {
        val preferences = ctx.getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
        with (preferences.edit()) {
            putString(Keys.ACCESS_TOKEN, entity.access)
            putString(Keys.REFRESH_TOKEN, entity.refresh)
            commit()
        }
    }

    fun removeTokens() {
        val preferences = ctx.getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
        with (preferences.edit()) {
            remove(Keys.ACCESS_TOKEN)
            remove(Keys.REFRESH_TOKEN)
            commit()
        }
    }

    fun getTokens() : TokensEntity? {
        val preferences = ctx.getSharedPreferences(Keys.PREFS_NAME, Context.MODE_PRIVATE)
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