package com.lamti.beatsnakechallenge.ui.components

import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel

@Composable
fun GameOverDialog(
    viewModel: MainViewModel,
    score: Int
) {
    AlertDialog(
        onDismissRequest = { viewModel.restartGame() },
        title = {
            Text(text = "Game Over")
        },
        text = {
            Text("Score: $score")
        },
        confirmButton = {
            Button(onClick = { viewModel.restartGame() }) {
                Text("Ok")
            }
        },
        dismissButton = {
            Button(
                onClick = { viewModel.restartGame() }) {
                Text("Cancel")
            }
        }
    )
}
