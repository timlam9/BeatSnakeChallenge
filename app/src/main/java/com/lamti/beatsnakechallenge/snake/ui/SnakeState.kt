package com.lamti.beatsnakechallenge.snake.ui

import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed

data class SnakeState(
    val score: Int,
    val board: Board,
    val controllers: SnakeControllers,
    val showSettings: Boolean,
    val snakeSpeed: SnakeSpeed
)
