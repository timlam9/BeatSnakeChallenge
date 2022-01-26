package com.lamti.beatsnakechallenge.snake.ui.components.board

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.lamti.beatsnakechallenge.R

@Composable
fun SnakeCell(modifier: Modifier, color: Color = MaterialTheme.colors.background) {
    BoxWithConstraints(
        modifier = modifier
            .clip(CircleShape)
            .background(color),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            modifier = Modifier.height(minWidth),
            painter = painterResource(id = R.drawable.ic_bolt),
            tint = Color.White,
            contentDescription = null
        )
    }
}
