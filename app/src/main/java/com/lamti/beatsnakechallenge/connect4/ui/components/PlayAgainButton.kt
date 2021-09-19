package com.lamti.beatsnakechallenge.connect4.ui.components

import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun PlayAgainButton(
    modifier: Modifier = Modifier,
    isGameOver: Boolean,
    onRestartClicked: () -> Unit
) {
    if (isGameOver) {
        Button(
            modifier = modifier,
            onClick = onRestartClicked
        ) {
            Text("Play again")
        }
    }
}
