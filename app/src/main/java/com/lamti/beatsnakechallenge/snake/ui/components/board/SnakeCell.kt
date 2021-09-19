package com.lamti.beatsnakechallenge.snake.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.lamti.beatsnakechallenge.R

@Composable
fun SnakeCell(modifier: Modifier, color: Color = MaterialTheme.colors.background) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_beat),
            tint = Color.White,
            contentDescription = null
        )
    }
}
