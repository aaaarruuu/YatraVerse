package com.yatraverse.data.local

import android.content.Context
import android.content.SharedPreferences

class SessionManager(context: Context) {

    private val prefs: SharedPreferences =
        context.getSharedPreferences(
            "yatraverse_session",
            Context.MODE_PRIVATE
        )

    fun saveSession(
        token: String,
        email: String,
        fullName: String
    ) {
        prefs.edit()
            .putString(KEY_TOKEN, token)
            .putString(KEY_EMAIL, email)
            .putString(KEY_FULL_NAME, fullName)
            .apply()
    }

    fun getToken(): String? =
        prefs.getString(KEY_TOKEN, null)

    fun getEmail(): String? =
        prefs.getString(KEY_EMAIL, null)

    fun getFullName(): String? =
        prefs.getString(KEY_FULL_NAME, null)

    fun isLoggedIn(): Boolean =
        getToken() != null

    fun clearSession() {
        prefs.edit().clear().apply()
    }

    companion object {
        private const val KEY_TOKEN = "token"
        private const val KEY_EMAIL = "email"
        private const val KEY_FULL_NAME = "full_name"
    }
}