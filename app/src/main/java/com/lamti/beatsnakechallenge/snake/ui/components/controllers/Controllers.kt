package com.lamti.beatsnakechallenge.snake.ui.components.controllers

import androidx.compose.runtime.Composable
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick.Joystick
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieController

@Composable
fun Controllers(
    controllers: SnakeControllers,
    onChangeDirection: (Board.Direction) -> Unit,
    restartGame: () -> Unit
) {
    when (controllers) {
        SnakeControllers.PieController -> {
            PieController(
                onUpClick = { onChangeDirection(Board.Direction.Up) },
                onLeftClick = { onChangeDirection(Board.Direction.Left) },
                onRightClick = { onChangeDirection(Board.Direction.Right) },
                onDownClick = { onChangeDirection(Board.Direction.Down) }
            )
        }
        SnakeControllers.Joystick -> {
            Joystick(
                onUpClick = { onChangeDirection(Board.Direction.Up) },
                onLeftClick = { onChangeDirection(Board.Direction.Left) },
                onRightClick = { onChangeDirection(Board.Direction.Right) },
                onDownClick = { onChangeDirection(Board.Direction.Down) },
                onCenterClick = { restartGame() }
            )
        }
    }
}
