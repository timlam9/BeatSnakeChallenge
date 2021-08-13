package com.lamti.beatsnakechallenge.snake.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.main.theme.Orange100

private val joystickColor = Orange100

@Composable
fun Joystick(
    onUpClick: () -> Unit,
    onLeftClick: () -> Unit,
    onRightClick: () -> Unit,
    onDownClick: () -> Unit,
    onCenterClick: () -> Unit
) {
    BoxWithConstraints(
        modifier = Modifier.fillMaxSize()
    ) {
        val smallSide = maxWidth / 8
        val largeSide = maxWidth / 6

        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                UpButton(
                    modifier = Modifier
                        .width(smallSide)
                        .height(largeSide)
                        .clickable { onUpClick() }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                LeftButton(
                    modifier = Modifier
                        .width(largeSide)
                        .height(smallSide)
                        .clickable { onLeftClick() }
                )
                Box(
                    modifier = Modifier
                        .size(smallSide)
                        .padding(6.dp)
                        .clip(CircleShape)
                        .background(joystickColor)
                        .clickable { onCenterClick() }
                )
                RightButton(
                    modifier = Modifier
                        .width(largeSide)
                        .height(smallSide)
                        .clickable { onRightClick() }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                DownButton(
                    modifier = Modifier
                        .width(smallSide)
                        .height(largeSide)
                        .clickable { onDownClick() }
                )
            }
        }
    }
}

@Composable
private fun UpButton(modifier: Modifier) {
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
