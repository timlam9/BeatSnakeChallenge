package com.lamti.beatsnakechallenge.snake.ui

import android.content.SharedPreferences

class SnakePreferences (
    private val sharedPreferences: SharedPreferences
) {

    companion object {

        const val SNAKE_PREFERENCES = "snake_preferences"
        const val KEY_NAME = "name"
        const val KEY_ID = "id"
        const val KEY_HIGHSCORE = "highscore"
        const val KEY_TOKEN = "token"
        const val KEY_EMAIL = "email"

    }

    fun saveUsername(name: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_NAME, name)
        editor.apply()
    }

    fun saveUserID(id: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_ID, id)
        editor.apply()
    }

    fun saveHighscore(score: Int) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putInt(KEY_HIGHSCORE, score)
        editor.apply()
    }

    fun saveToken(token: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun saveEmail(email: String) {
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(KEY_EMAIL, email)
        editor.apply()
    }

    fun getUsername(): String = sharedPreferences.getString(KEY_NAME, "") ?: ""

    fun getID(): String = sharedPreferences.getString(KEY_ID, "") ?: ""

    fun getHighscore(): Int = sharedPreferences.getInt(KEY_HIGHSCORE, 0)

    fun getAuthToken(): String = sharedPreferences.getString(KEY_TOKEN, "") ?: ""

    fun getEmail(): String = sharedPreferences.getString(KEY_EMAIL, "") ?: ""

}
