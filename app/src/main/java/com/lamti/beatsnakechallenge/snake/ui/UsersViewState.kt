package com.lamti.beatsnakechallenge.snake.ui

import com.lamti.beatsnakechallenge.snake.data.User

sealed class UsersViewState {

    object Loading : UsersViewState()

    data class Error(val message: String) : UsersViewState()

    data class Success(val users: List<User>) : UsersViewState()

}
