package com.lamti.beatsnakechallenge.connect4.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.lamti.beatsnakechallenge.connect4.domain.Error
import com.lamti.beatsnakechallenge.main.theme.Orange100

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    error: Error?
) {
    if (error != null) {
        InfoText(
            modifier = modifier,
            text = error.message,
            color = Orange100,
            textStyle = MaterialTheme.typography.h5
        )
    }
}
