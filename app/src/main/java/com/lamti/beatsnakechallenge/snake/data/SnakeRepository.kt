package com.lamti.beatsnakechallenge.snake.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.lamti.beatsnakechallenge.snake.ui.SnakePreferences
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class SnakeRepository(private val preferences: SnakePreferences) {

    companion object {

        private const val BASE_URL = "https://beatsnake.herokuapp.com/"

    }

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val api = Retrofit.Builder()
        .client(
            OkHttpClient().newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS).build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(SnakeApi::class.java)

    suspend fun updateUser(user: User): String = api.updateUser(user)

    suspend fun getUsers(): List<User> = api.getUsers()

    fun saveUserID(id: String) {
        preferences.saveUserID(id)
    }

    fun saveUsername(name: String) {
        preferences.saveUsername(name)
    }

    fun saveHighscore(score: Int) {
        preferences.saveHighscore(score)
    }

    fun getID(): String = preferences.getID()

    fun getUsername(): String = preferences.getUsername()

    fun getHighscore(): Int = preferences.getHighscore()


}
