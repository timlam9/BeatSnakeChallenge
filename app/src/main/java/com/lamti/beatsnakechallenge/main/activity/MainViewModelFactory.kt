package com.lamti.beatsnakechallenge.main.activity

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.lamti.beatsnakechallenge.snake.data.SnakeRepository
import com.lamti.beatsnakechallenge.snake.domain.Game

class MainViewModelFactory(
    val repository: SnakeRepository,
    val game: Game
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                repository = repository,
                game = game
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}
