package com.lamti.beatsnakechallenge.ui.components

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.domain.Point
import com.lamti.beatsnakechallenge.ui.activity.MainActivity
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel
import com.lamti.beatsnakechallenge.ui.theme.Navy100

@Composable
fun Snake(viewModel: MainViewModel) {
    val board by viewModel.board.collectAsState()
    val score by viewModel.score.collectAsState()
    val animatedColor = crashAnimatedColor()

    Column(modifier = Modifier.fillMaxSize()) {
        Score(score = score.toString()) {
            viewModel.onSettingsClicked()
        }
        SnakeBoard(board = board) { point ->
            when {
                board.driver.hasCrashed() && board.driver.head == point -> animatedColor.value
                else -> viewModel.colorCell(point)
            }
        }
        Controllers(viewModel.controllers, viewModel)
        if (viewModel.board.value.driver.hasCrashed()) {
            GameOverDialog(viewModel, score)
        }
        if (viewModel.showSettings) {
            SettingsDialog(viewModel = viewModel)
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
                durationMillis = MainActivity.CRASH_ANIMATION_DURATION,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )
}

@Composable
fun SnakeBoard(board: Board, colorCell: (Point) -> Color) {
    Column(
        modifier = Modifier.padding(start = 20.dp, end = 20.dp)
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
fun Cell(modifier: Modifier, color: Color = MaterialTheme.colors.onBackground) {
    val dayNightColor = if (color == Color.White) MaterialTheme.colors.onBackground else color

    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    ) {
        if (dayNightColor == MaterialTheme.colors.onBackground) {
            Icon(
                painter = painterResource(id = R.drawable.ic_beat),
                tint = dayNightColor,
                contentDescription = null
            )
        } else {
            Image(
                painter = painterResource(id = R.drawable.ic_beat),
                contentDescription = null
            )
        }
    }
}