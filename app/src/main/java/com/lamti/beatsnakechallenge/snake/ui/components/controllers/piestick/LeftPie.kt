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
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Left
import com.lamti.beatsnakechallenge.snake.ui.controllerColorTwo
import com.lamti.beatsnakechallenge.snake.ui.separatorColor

@Composable
fun LeftPie(modifier: Modifier, onClick: () -> Unit) {
    val leftShape = PieShape(Left)
    Box(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 0.dp.toPx()
                shape = leftShape
                clip = true
            }
            .background(color = controllerColorTwo)
            .drawBehind {
                scale(scale = 1f) {
                    drawPath(
                        path = leftShape.drawPath(size = size),
                        color = separatorColor,
                        style = Stroke(
                            width = 6.dp.toPx()
                        )
                    )
                }
            }
            .clickable { onClick() }
    )
}
