package com.lamti.beatsnakechallenge.snake.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.lamti.beatsnakechallenge.main.theme.Navy100
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.Point
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.snake.ui.CRASH_ANIMATION_DURATION
import com.lamti.beatsnakechallenge.snake.ui.SnakeState
import com.lamti.beatsnakechallenge.snake.ui.components.board.SnakeBoard
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.Controllers
import com.lamti.beatsnakechallenge.snake.ui.components.dialogs.GameOverDialog
import com.lamti.beatsnakechallenge.snake.ui.components.dialogs.SettingsDialog
import com.lamti.beatsnakechallenge.snake.ui.components.score.Score

@Composable
fun SnakeScreen(
    snakeState: SnakeState,
    onSettingsClicked: () -> Unit,
    onHighscoresClicked: () -> Unit,
    highscore: Int,
    onSpeedChanged: (SnakeSpeed) -> Unit,
    onControllerChanged: (SnakeControllers) -> Unit,
    colorCell: (Point) -> Color,
    gameOver: (Int) -> Unit,
    onChangeDirection: (Board.Direction) -> Unit,
    restartGame: () -> Unit
) {
    val animatedColor = crashAnimatedColor()

    Column(modifier = Modifier.fillMaxSize()) {
        Score(score = snakeState.score.toString()) { onSettingsClicked() }
        SnakeBoard(board = snakeState.board) { point ->
            when {
                snakeState.board.driver.hasCrashed() && snakeState.board.driver.head == point -> animatedColor.value
                else -> colorCell(point)
            }
        }
        Controllers(
            controllers = snakeState.controllers,
            onChangeDirection = onChangeDirection,
            restartGame = restartGame
        )
        if (snakeState.board.driver.hasCrashed()) {
            LaunchedEffect(true) { gameOver(snakeState.score) }
            GameOverDialog(snakeState.score, restartGame)
        }
        if (snakeState.showSettings) {
            SettingsDialog(
                onSettingsClicked = onSettingsClicked,
                onHighscoresClicked = onHighscoresClicked,
                currentSnakeSpeed = snakeState.snakeSpeed,
                currentController = snakeState.controllers,
                highscore = highscore,
                onSpeedChanged = onSpeedChanged,
                onControllerChanged = onControllerChanged
            )
        }
    }
}

@Composable
private fun crashAnimatedColor(): State<Color> {
    val infiniteTransition = rememberInfiniteTransition()
    return infiniteTransition.animateColor(
        initialValue = Color.Red,
        targetValue = Navy100,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = CRASH_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
}
