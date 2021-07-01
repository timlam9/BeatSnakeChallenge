package com.lamti.beatsnakechallenge.ui.components

import androidx.compose.runtime.Composable
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.domain.SnakeControllers
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel

@Composable
fun Controllers(
    controllers: SnakeControllers,
    viewModel: MainViewModel
) {
    when (controllers) {
        SnakeControllers.PieController -> {
            PieController(
                onUpClick = { viewModel.changeDirection(Board.Direction.Up) },
                onLeftClick = { viewModel.changeDirection(Board.Direction.Left) },
                onRightClick = { viewModel.changeDirection(Board.Direction.Right) },
                onDownClick = { viewModel.changeDirection(Board.Direction.Down) }
            )
        }
        SnakeControllers.Joystick -> {
            Joystick(
                onUpClick = { viewModel.changeDirection(Board.Direction.Up) },
                onLeftClick = { viewModel.changeDirection(Board.Direction.Left) },
                onRightClick = { viewModel.changeDirection(Board.Direction.Right) },
                onDownClick = { viewModel.changeDirection(Board.Direction.Down) },
                onCenterClick = { viewModel.restartGame() }
            )
        }
    }
}