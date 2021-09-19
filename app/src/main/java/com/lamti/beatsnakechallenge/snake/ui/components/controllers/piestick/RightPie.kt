package com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Right
import com.lamti.beatsnakechallenge.snake.ui.controllerColorTwo
import com.lamti.beatsnakechallenge.snake.ui.separatorColor

@Composable
fun RightPie(modifier: Modifier, onClick: () -> Unit) {
    val rightShape = PieShape(Right)
    Box(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 0.dp.toPx()
                shape = rightShape
                clip = true
            }
            .background(color = controllerColorTwo)
            .drawBehind {
                scale(scale = 1f) {
                    drawPath(
                        path = rightShape.drawPath(size = size),
                        color = separatorColor,
                        style = Stroke(width = 6.dp.toPx())
                    )
                }
            }
            .clickable { onClick() }
    )
}
