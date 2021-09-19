package com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor

@Composable
fun DownButton(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        val path = Path()
        val padding = 20f
        val yPoint = size.height - (size.height / 3 + size.height / 4)
        val centerPointX = size.width / 2

        path.moveTo(padding, yPoint)
        path.lineTo(centerPointX, padding)
        path.lineTo(size.width - padding, yPoint)
        path.lineTo(size.width - padding, size.height - padding)
        path.lineTo(padding, size.height - padding)

        drawPath(
            path = path,
            brush = SolidColor(joystickColor)
        )
    }
}
