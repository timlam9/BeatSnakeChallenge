package com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick

import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor

@Composable
fun RightButton(modifier: Modifier) {
    Canvas(
        modifier = modifier
    ) {
        val path = Path()
        val padding = 20f
        val xPoint = size.width - (size.width / 3 + size.width / 4)
        val yPoint = size.height - padding
        val centerPointX = size.width - padding
        val centerPointY = size.height / 2

        path.moveTo(padding, centerPointY)
        path.lineTo(xPoint, padding)
        path.lineTo(centerPointX, padding)
        path.lineTo(centerPointX, yPoint)
        path.lineTo(xPoint, yPoint)

        drawPath(
            path = path,
            brush = SolidColor(joystickColor)
        )
    }
}
