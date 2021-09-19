package com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor

@Composable
fun UpButton(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        val path = Path()
        val padding = 20f
        val yPoint = size.height / 3 + size.height / 4
        val centerPointX = size.width / 2
        val centerPointY = size.height - padding

        path.moveTo(padding, padding)
        path.lineTo(size.width - padding, padding)
        path.lineTo(size.width - padding, yPoint)
        path.lineTo(centerPointX, centerPointY)
        path.lineTo(padding, yPoint)

        drawPath(
            path = path,
            brush = SolidColor(joystickColor)
        )
    }
}
