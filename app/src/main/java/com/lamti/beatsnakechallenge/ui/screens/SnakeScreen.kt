package com.lamti.beatsnakechallenge.ui.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.data.SnakeRepository
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.domain.Point
import com.lamti.beatsnakechallenge.domain.SnakeControllers
import com.lamti.beatsnakechallenge.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel
import com.lamti.beatsnakechallenge.ui.components.Controllers
import com.lamti.beatsnakechallenge.ui.components.GameOverDialog
import com.lamti.beatsnakechallenge.ui.components.Score
import com.lamti.beatsnakechallenge.ui.components.SettingsDialog
import com.lamti.beatsnakechallenge.ui.theme.CRASH_ANIMATION_DURATION
import com.lamti.beatsnakechallenge.ui.theme.Navy100

data class SnakeState(
    val score: Int,
    val board: Board,
    val controllers: SnakeControllers,
    val showSettings: Boolean,
    val snakeSpeed: SnakeSpeed
)

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

@Composable
fun SnakeBoard(board: Board, colorCell: (Point) -> Color) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colors.background
                ),
                shape = RoundedCornerShape(2)
            )
            .padding(4.dp)
    ) {
        board.grid.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { point ->
                    Cell(
                        modifier = Modifier.weight(1f),
                        color = colorCell(point)
                    )
                }
            }
        }
    }
}

@Composable
fun Cell(modifier: Modifier, color: Color = MaterialTheme.colors.background) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_beat),
            tint = Color.White,
            contentDescription = null
        )
    }
}
