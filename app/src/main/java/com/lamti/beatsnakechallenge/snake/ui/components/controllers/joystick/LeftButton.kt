package com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor

@Composable
fun LeftButton(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        val path = Path()
        val padding = 20f
        val xPoint = size.width / 3 + size.width / 4
        val yPoint = size.height - padding
        val centerPointX = size.width - padding
        val centerPointY = size.height / 2

        path.moveTo(padding, padding)
        path.lineTo(padding, yPoint)
        path.lineTo(xPoint, yPoint)
        path.lineTo(centerPointX, centerPointY)
        path.lineTo(xPoint, padding)

        drawPath(
            path = path,
            brush = SolidColor(joystickColor)
        )
    }
}
