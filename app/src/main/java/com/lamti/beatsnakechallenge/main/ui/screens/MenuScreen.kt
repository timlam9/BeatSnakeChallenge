package com.lamti.beatsnakechallenge.main.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.main.ui.theme.Mint75
import com.lamti.beatsnakechallenge.main.ui.theme.Navy50
import com.lamti.beatsnakechallenge.main.ui.theme.Orange75
import com.lamti.beatsnakechallenge.snake.ui.BUTTON_CORNER_SIZE
import com.lamti.beatsnakechallenge.snake.ui.BUTTON_HEIGHT

@Composable
fun MenuScreen(onConnectFourClicked: () -> Unit, onSnakeClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.25f))
        Text(
            text = stringResource(R.string.go_games),
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.background,
                fontWeight = Bold
            )
        )
        Text(
            text = stringResource(R.string.what_do_you_play),
            style = MaterialTheme.typography.h5.copy(color = Navy50)
        )
        Spacer(modifier = Modifier.weight(2f))
        SnakeButton(modifier = Modifier.padding(20.dp)) {
            onSnakeClicked()
        }
        ConnectFourButton(modifier = Modifier.padding(20.dp)) {
            onConnectFourClicked()
        }
    }
}

@Composable
fun ConnectFourButton(modifier: Modifier = Modifier, onConnectFourClicked: () -> Unit) {
    BeatGamesButton(
        modifier = modifier,
        text = "Connect 4",
        color = ButtonDefaults.buttonColors(backgroundColor = Mint75)
    ) {
        onConnectFourClicked()
    }
}

@Composable
fun SnakeButton(modifier: Modifier = Modifier, onSnakeClicked: () -> Unit) {
    BeatGamesButton(
        modifier = modifier,
        text = "Snake",
        color = ButtonDefaults.buttonColors(backgroundColor = Orange75)
    ) {
        onSnakeClicked()
    }
}

@Composable
fun BeatGamesButton(
    modifier: Modifier = Modifier,
    text: String = "",
    textColor: Color = Color.White,
    color: ButtonColors = ButtonDefaults.buttonColors(backgroundColor = Navy50),
    shape: Shape = MaterialTheme.shapes.small.copy(CornerSize(BUTTON_CORNER_SIZE)),
    onclick: () -> Unit
) {
    Button(
        onClick = onclick,
        modifier = modifier
            .height(BUTTON_HEIGHT)
            .fillMaxWidth(),
        shape = shape,
        colors = color
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button.copy(
                color = textColor,
                fontWeight = Bold
            )
        )
    }
}

@Preview
@Composable
fun MenuScreenPreview() {
    MenuScreen({}, {})
}
