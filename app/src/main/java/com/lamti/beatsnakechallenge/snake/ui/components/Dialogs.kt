package com.lamti.beatsnakechallenge.snake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers.Joystick
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers.PieController
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed.Fast
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed.Normal
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed.Slow
import com.lamti.beatsnakechallenge.main.theme.Orange100

@Composable
fun GameOverDialog(
    score: Int,
    restartGame: () -> Unit
) {
    AlertDialog(
        onDismissRequest = { restartGame() },
        title = { Text(text = "Game Over") },
        text = { Text("Final score: $score") },
        confirmButton = {
            Button(onClick = { restartGame() }) {
                Text("Play again")
            }
        }
    )
}

@Composable
fun SettingsDialog(
    onSettingsClicked: () -> Unit,
    onHighscoresClicked: () -> Unit,
    currentSnakeSpeed: SnakeSpeed,
    currentController: SnakeControllers,
    highscore: Int,
    onSpeedChanged: (SnakeSpeed) -> Unit,
    onControllerChanged: (SnakeControllers) -> Unit,
) {
    AlertDialog(
        onDismissRequest = onSettingsClicked,
        title = { Text(text = "Settings") },
        text = {
            SettingsDialogContent(
                currentSnakeSpeed = currentSnakeSpeed,
                currentController = currentController,
                highscore = highscore,
                onSpeedChanged = onSpeedChanged,
                onControllerChanged = onControllerChanged
            ) {
                onHighscoresClicked()
            }
        },
        confirmButton = {
            Button(onClick = onSettingsClicked) {
                Text("Resume Game")
            }
        }
    )

}

@Composable
private fun SettingsDialogContent(
    currentSnakeSpeed: SnakeSpeed,
    currentController: SnakeControllers,
    highscore: Int,
    onSpeedChanged: (SnakeSpeed) -> Unit,
    onControllerChanged: (SnakeControllers) -> Unit,
    onHighscoresClicked: () -> Unit
) {
    Column {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = buildAnnotatedString {
                    append("Highscore: ")
                    withStyle(
                        style = SpanStyle(fontWeight = FontWeight.W900, color = Orange100)
                    ) {
                        append("$highscore")
                    }
                }
            )
            Spacer(modifier = Modifier.weight(4f))
            OutlinedButton(onClick = onHighscoresClicked) {
                Text("See highscores")
            }
        }
        Text(text = "Snake speed:")
        Row {
            IconButton(
                onClick = { onSpeedChanged(Slow) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = speedBgColor(currentSnakeSpeed, Slow))
            ) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Slow",
                    tint = Color.White
                )
            }
            IconButton(
                onClick = { onSpeedChanged(Normal) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = speedBgColor(currentSnakeSpeed, Normal))
            ) {
                Icon(
                    Icons.Filled.ThumbUp,
                    contentDescription = "Normal",
                    tint = Color.Yellow
                )
            }
            IconButton(
                onClick = { onSpeedChanged(Fast) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = speedBgColor(currentSnakeSpeed, Fast))
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
                onClick = { onControllerChanged(PieController) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = controllersBgColor(currentController, PieController))
            ) {
                Icon(
                    Icons.Filled.AddCircle,
                    contentDescription = "Pie controller",
                    tint = Color.Yellow
                )
            }
            IconButton(
                onClick = { onControllerChanged(Joystick) },
                modifier = Modifier
                    .clip(CircleShape)
                    .background(color = controllersBgColor(currentController, Joystick))
            ) {
                Icon(
                    Icons.Filled.AddCircle,
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
