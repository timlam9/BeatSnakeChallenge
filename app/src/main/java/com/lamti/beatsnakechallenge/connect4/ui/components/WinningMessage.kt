package com.lamti.beatsnakechallenge.connect4.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus
import com.lamti.beatsnakechallenge.main.theme.Mint100

@Composable
fun WinningMessage(
    modifier: Modifier = Modifier,
    status: GameStatus
) {
    if (status != GameStatus.Playing) {
        InfoText(
            modifier = modifier,
            text = status.getMessage(),
            color = Mint100,
            textStyle = MaterialTheme.typography.h5
        )
    }
}
