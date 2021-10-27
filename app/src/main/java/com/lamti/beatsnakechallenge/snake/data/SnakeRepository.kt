package com.lamti.beatsnakechallenge.snake.data

import com.lamti.beatsnakechallenge.main.domain.RegisteredUser
import com.lamti.beatsnakechallenge.main.domain.RegistrationCredentials
import com.lamti.beatsnakechallenge.snake.ui.SnakePreferences
import com.lamti.beatsnakechallenge.snake.ui.UsersViewState
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SnakeRepository @Inject constructor(
    private val preferences: SnakePreferences,
    private val registerApi: RegisterApi,
    private val api: SnakeApi
) {

    suspend fun register(credentials: RegistrationCredentials): RegisteredUser = registerApi.register(credentials).apply {
        preferences.saveToken(token)
        preferences.saveEmail(email)
        preferences.saveUsername(name)
    }

    suspend fun updateUser(): User {
        val updatedUser = getUser()
        api.updateUser(updatedUser)

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
