package com.lamti.beatsnakechallenge.snake.ui.components.controllers.joystick

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.main.theme.Orange100

val joystickColor = Orange100

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
