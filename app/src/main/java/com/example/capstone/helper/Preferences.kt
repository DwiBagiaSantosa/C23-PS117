package com.example.capstone.helper

import android.content.Context
import com.example.capstone.models.User

class Preferences(context: Context) {
    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_TOKEN = "token"
        private const val KEY_SESSION = "session"
        private const val KEY_TEMP_USER = "temp_user"
    }

    private val sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) {
            sharedPreferences.edit().putString(KEY_TOKEN, value).apply()
        }

    var session: Boolean
        get() = sharedPreferences.getBoolean(KEY_SESSION, false)
        set(value) {
            sharedPreferences.edit().putBoolean(KEY_SESSION, value).apply()
        }

    var tempUser: User?
        get() {
            val userJson = sharedPreferences.getString(KEY_TEMP_USER, null)
            return if (userJson != null) {
                User.fromJson(userJson)
            } else {
                null
            }
        }
        set(value) {
            val userJson = value?.toJson()
            sharedPreferences.edit().putString(KEY_TEMP_USER, userJson).apply()
        }

    fun clearData() {
        sharedPreferences.edit().clear().apply()
    }
}
