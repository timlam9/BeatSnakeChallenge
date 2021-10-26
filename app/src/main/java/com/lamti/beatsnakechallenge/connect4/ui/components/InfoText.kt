package com.lamti.beatsnakechallenge.connect4.ui.components

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import com.lamti.beatsnakechallenge.main.ui.theme.Navy10

@Composable
fun InfoText(
    modifier: Modifier = Modifier,
    text: String = "",
    color: Color = Navy10,
    textStyle: TextStyle = MaterialTheme.typography.body1
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        style = textStyle
    )
}
