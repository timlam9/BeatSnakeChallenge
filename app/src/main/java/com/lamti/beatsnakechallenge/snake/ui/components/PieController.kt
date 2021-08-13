package com.lamti.beatsnakechallenge.snake.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.snake.ui.components.PieShape.PieControllerType.*
import com.lamti.beatsnakechallenge.snake.ui.controllerColorOne
import com.lamti.beatsnakechallenge.snake.ui.controllerColorTwo
import com.lamti.beatsnakechallenge.snake.ui.separatorColor

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

@Composable
private fun LeftPie(modifier: Modifier, onClick: () -> Unit) {
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

@Composable
private fun UpPie(modifier: Modifier, onClick: () -> Unit) {
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

@Composable
private fun RightPie(modifier: Modifier, onClick: () -> Unit) {
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

@Composable
private fun DownPie(modifier: Modifier, onClick: () -> Unit) {
    val downShape = PieShape(Down)
    Box(
        modifier = modifier
            .graphicsLayer {
                shadowElevation = 0.dp.toPx()
                shape = downShape
                clip = true
            }
            .background(color = controllerColorOne)
            .drawBehind {
                scale(scale = 1f) {
                    drawPath(
                        path = downShape.drawPath(size = size),
                        color = separatorColor,
                        style = Stroke(width = 6.dp.toPx())
                    )
                }
            }
            .clickable { onClick() }
    )
}

class PieShape(private val type: PieControllerType) : Shape {

    enum class PieControllerType {

        Up,
        Right,
        Down,
        Left

    }

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        return Outline.Generic(
            path = drawPath(size)
        )
    }

    fun drawPath(size: Size) = when (type) {
        Up -> drawUpPath(size)
        Right -> drawRightPath(size)
        Down -> drawDownPath(size)
        Left -> drawLeftPath(size)
    }

    private fun drawLeftPath(size: Size): Path {
        return Path().apply {
            reset()
            arcTo(
                rect = Rect(
                    Offset(0f, 0f),
                    Size(size.height, size.height)
                ),
                startAngleDegrees = 135f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(x = size.height / 2, y = size.width / 2)
            close()
        }
    }

    private fun drawDownPath(size: Size): Path {
        return Path().apply {
            reset()
            arcTo(
                rect = Rect(
                    Offset(0f, 0f),
                    Size(size.height, size.height)
                ),
                startAngleDegrees = 45f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(x = size.height / 2, y = size.width / 2)
            close()
        }
    }

    private fun drawRightPath(size: Size): Path {
        return Path().apply {
            reset()
            arcTo(
                rect = Rect(
                    Offset(0f, 0f),
                    Size(size.height, size.height)
                ),
                startAngleDegrees = -45f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(x = size.height / 2, y = size.width / 2)
            close()
        }
    }

    private fun drawUpPath(size: Size): Path {
        return Path().apply {
            reset()
            arcTo(
                rect = Rect(
                    Offset(0f, 0f),
                    Size(size.height, size.height)
                ),
                startAngleDegrees = -135f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            lineTo(x = size.height / 2, y = size.width / 2)
            close()
        }
    }

}

@Preview
@Composable
fun PiePreview() {
    PieController()
}
