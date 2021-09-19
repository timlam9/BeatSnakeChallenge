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
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Up
import com.lamti.beatsnakechallenge.snake.ui.controllerColorOne
import com.lamti.beatsnakechallenge.snake.ui.separatorColor

@Composable
fun UpPie(modifier: Modifier, onClick: () -> Unit) {
    val upShape = PieShape(Up)
    Box(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 0.dp.toPx()
                shape = upShape
                clip = true
            }
            .background(color = controllerColorOne)
            .drawBehind {
                scale(scale = 1f) {
                    drawPath(
                        path = upShape.drawPath(size = size),
                        color = separatorColor,
                        style = Stroke(width = 6.dp.toPx())
                    )
                }
            }
            .clickable { onClick() }
    )
}
