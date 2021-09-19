package com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun PieController(
    onUpClick: () -> Unit = {},
    onLeftClick: () -> Unit = {},
    onRightClick: () -> Unit = {},
    onDownClick: () -> Unit = {},
) {
    val size = 200.dp
    val padding = 16.dp
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LeftPie(
            modifier = Modifier
                .size(size)
                .padding(padding),
            onClick = onLeftClick
        )
        UpPie(
            modifier = Modifier
                .size(size)
                .padding(padding),
            onClick = onUpClick
        )
        RightPie(
            modifier = Modifier
                .size(size)
                .padding(padding),
            onClick = onRightClick
        )
        DownPie(
            modifier = Modifier
                .size(size)
                .padding(padding),
            onClick = onDownClick
        )
    }
}

@Preview
@Composable
fun PiePreview() {
    PieController()
}
