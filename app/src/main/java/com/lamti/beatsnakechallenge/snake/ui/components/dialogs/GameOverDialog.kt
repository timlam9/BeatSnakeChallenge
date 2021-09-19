package com.lamti.beatsnakechallenge.snake.ui.components.dialogs

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

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
