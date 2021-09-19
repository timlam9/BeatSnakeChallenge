package com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Down
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Left
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Right
import com.lamti.beatsnakechallenge.snake.ui.components.controllers.piestick.PieShape.PieControllerType.Up

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

    fun drawPath(size: Size): Path = when (type) {
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
