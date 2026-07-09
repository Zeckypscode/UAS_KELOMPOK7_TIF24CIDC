package com.example.siapsen.utils

import android.content.Context

class SessionManager(context: Context) {

    private val pref =
        context.getSharedPreferences("SIAPSEN", Context.MODE_PRIVATE)

    fun saveLogin(
        username: String,
        role: String
    ) {
        pref.edit()
            .putBoolean("login", true)
            .putString("username", username)
            .putString("role", role)
            .apply()
    }

    fun isLogin(): Boolean {
        return pref.getBoolean("login", false)
    }

    fun getUsername(): String {
        return pref.getString("username", "") ?: ""
    }

    fun getRole(): String {
        return pref.getString("role", "") ?: ""
    }

    fun logout() {
        pref.edit().clear().apply()
    }
}