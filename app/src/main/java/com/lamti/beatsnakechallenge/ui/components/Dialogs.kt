package com.lamti.beatsnakechallenge.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.domain.SnakeControllers
import com.lamti.beatsnakechallenge.domain.SnakeControllers.Joystick
import com.lamti.beatsnakechallenge.domain.SnakeControllers.PieController
import com.lamti.beatsnakechallenge.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.domain.SnakeSpeed.*
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel

@Composable
fun GameOverDialog(
    viewModel: MainViewModel,
    score: Int
) {
    AlertDialog(
        onDismissRequest = { viewModel.restartGame() },
        title = { Text(text = "Game Over") },
        text = { Text("Final score: $score") },
        confirmButton = {
            Button(onClick = { viewModel.restartGame() }) {
                Text("Play again")
            }
        }
    )
}

@Composable
fun SettingsDialog(viewModel: MainViewModel) {
    AlertDialog(
        onDismissRequest = { viewModel.onSettingsClicked() },
        title = { Text(text = "Settings") },
        text = { SettingsDialogContent(viewModel) },
        confirmButton = {
            Button(onClick = { viewModel.onSettingsClicked() }) {
                Text("Resume Game")
            }
        }
    )
}

@Composable
private fun SettingsDialogContent(viewModel: MainViewModel) {
    Column {
        Text("Snake speed:")
        Row {
            IconButton(
                onClick = { viewModel.changeSnakeSpeed(Slow) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = speedBgColor(viewModel.snakeSpeed, Slow))
            ) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Slow",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { viewModel.changeSnakeSpeed(Normal) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = speedBgColor(viewModel.snakeSpeed, Normal))
            ) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Normal",
                    tint = Color.Yellow
                )
            }
            IconButton(
                onClick = { viewModel.changeSnakeSpeed(Fast) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = speedBgColor(viewModel.snakeSpeed, Fast))
            ) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Fast",
                    tint = Color.Red
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text("Controller:")
        Row {
            IconButton(
                onClick = { viewModel.setController(PieController) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = controllersBgColor(viewModel.controllers, PieController))
            ) {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Pie controller",
                    tint = Color.Yellow
                )
            }
            IconButton(
                onClick = { viewModel.setController(Joystick) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = controllersBgColor(viewModel.controllers, Joystick))
            ) {
                Icon(
                    Icons.Filled.Phone,
                    contentDescription = "Joystick",
                    tint = Color.Red
                )
            }
        }
    }
}

@Composable
fun speedBgColor(currentSpeed: SnakeSpeed, speed: SnakeSpeed): Color =
    if (currentSpeed == speed) MaterialTheme.colors.primary else Color.Transparent


@Composable
private fun controllersBgColor(currentController: SnakeControllers, controller: SnakeControllers) =
    if (currentController == controller) MaterialTheme.colors.primary else Color.Transparent
