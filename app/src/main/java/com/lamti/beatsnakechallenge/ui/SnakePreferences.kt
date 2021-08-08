package com.lamti.beatsnakechallenge.ui

import android.content.Context
import android.content.SharedPreferences

class SnakePreferences {

    companion object {

        const val SNAKE_PREFERENCES = "snake_preferences"
        const val NAME_KEY = "name"

    }

    private var sharedPreferences: SharedPreferences? = null

    fun initSharedPrefs(context: Context) {
        sharedPreferences = context.getSharedPreferences(SNAKE_PREFERENCES, Context.MODE_PRIVATE)
    }

    fun saveUsername(name: String) {
        if (sharedPreferences == null) return
        val editor: SharedPreferences.Editor = sharedPreferences!!.edit()
        editor.putString(NAME_KEY, name)
        editor.apply()
    }

    fun getUsername(): String = when (sharedPreferences) {
        null -> ""
        else -> sharedPreferences!!.getString(NAME_KEY, "") ?: ""
    }

}
