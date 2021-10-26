package com.lamti.beatsnakechallenge.snake.data

import android.util.Log
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.google.gson.GsonBuilder
import com.google.gson.Gson
import com.lamti.beatsnakechallenge.main.domain.RegisteredUser
import com.lamti.beatsnakechallenge.main.domain.RegistrationCredentials
import com.lamti.beatsnakechallenge.snake.ui.UsersViewState
import com.lamti.beatsnakechallenge.snake.ui.SnakePreferences
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

class SnakeRepository constructor(
    private val preferences: SnakePreferences
) {

    companion object {

        //        private const val BASE_URL = "https://beatsnake.herokuapp.com/"
        private const val BASE_URL = "http://192.168.1.101:8080/"

    }

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val client: OkHttpClient.Builder = OkHttpClient()
        .newBuilder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)

    private val registerApi = Retrofit.Builder()
        .client(client.build())
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(SnakeApi::class.java)

    private val api = Retrofit.Builder()
        .client(
            client.addInterceptor { chain ->
                val newRequest = chain.request().newBuilder()
                    .addHeader("Authorization", "Bearer ${preferences.getAuthToken()}")
                    .build()
                chain.proceed(newRequest)
            }.build()
        )
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
        .create(SnakeApi::class.java)

    suspend fun register(credentials: RegistrationCredentials): RegisteredUser = registerApi.register(credentials).apply {
        preferences.saveToken(token)
        preferences.saveEmail(email)
        preferences.saveUsername(name)
    }

    suspend fun updateUser(): User {
        val updatedUser = getUser()
        try {
            api.updateUser(updatedUser)
        } catch (e: Exception) {
            Log.d("TAGARA", e.message.toString())
        }

        return updatedUser
    }

    suspend fun getUsers(): UsersViewState = try {
        UsersViewState.Success(api.getUsers().sortedByDescending { it.highscore })
    } catch (e: Exception) {
        UsersViewState.Error(e.message ?: "error")
    }


    fun saveHighscore(score: Int) {
        preferences.saveHighscore(score)
    }

    fun getUser(): User = preferences.run {
        User(
            id = getID(),
            name = getUsername(),
            highscore = getHighscore(),
            email = getEmail()
        )
    }

    fun getHighscore(): Int = preferences.getHighscore()

}
