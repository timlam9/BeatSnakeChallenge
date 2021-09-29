package com.lamti.beatsnakechallenge.snake.ui.components.controllers

import androidx.compose.runtime.Composable
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Down
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Left
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Right
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Up
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick.Joystick
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieController

@Composable
fun Controllers(
    controllers: SnakeControllers,
    onChangeDirection: (Direction) -> Unit,
    restartGame: () -> Unit
) {
    when (controllers) {
        SnakeControllers.PieController -> {
            PieController(
                onUpClick = { onChangeDirection(Up) },
                onLeftClick = { onChangeDirection(Left) },
                onRightClick = { onChangeDirection(Right) },
                onDownClick = { onChangeDirection(Down) }
            )
        }
        SnakeControllers.Joystick -> {
            Joystick(
                onUpClick = { onChangeDirection(Up) },
                onLeftClick = { onChangeDirection(Left) },
                onRightClick = { onChangeDirection(Right) },
                onDownClick = { onChangeDirection(Down) },
                onCenterClick = { restartGame() }
            )
        }
    }
}
