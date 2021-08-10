package com.lamti.beatsnakechallenge.ui.components

import androidx.compose.runtime.Composable
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.domain.SnakeControllers

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
