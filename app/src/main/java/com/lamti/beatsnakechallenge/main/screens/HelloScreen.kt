package com.lamti.beatsnakechallenge.main.screens

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.snake.ui.BUTTON_CORNER_SIZE
import com.lamti.beatsnakechallenge.snake.ui.BUTTON_HEIGHT
import com.lamti.beatsnakechallenge.main.theme.Navy50
import com.lamti.beatsnakechallenge.main.theme.Orange75

@Composable
fun HelloScreen(onPlayClicked: (String) -> Unit = { }) {
    val context = LocalContext.current
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = stringResource(R.string.hello_there),
            style = MaterialTheme.typography.h4.copy(color = Color.White, fontWeight = Bold)
        )
        Text(
            text = stringResource(R.string.welcome_to_snake),
            style = MaterialTheme.typography.h5.copy(color = Navy50)
        )
        Text(
            text = stringResource(R.string.whats_your_name),
            style = MaterialTheme.typography.h5.copy(color = Navy50)
        )
        Spacer(modifier = Modifier.weight(1f))
        NameTextField(text = text) {
            text = it
        }
        Spacer(modifier = Modifier.weight(4f))
        PlayButton {
            when {
                text.isEmpty() -> Toast(context).apply {
                    setText("Please type your name first.")
                    show()
                }
                else -> onPlayClicked(text)
            }
        }
    }
}

@Composable
fun NameTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String = stringResource(R.string.type_your_name),
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = text,
        onValueChange = { onValueChange(it) },
        label = { Text(label) },
        colors = TextFieldDefaults.outlinedTextFieldColors(),
        maxLines = 1,
        singleLine = true,
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Text,
            capitalization = KeyboardCapitalization.Words
        )
    )
}

@Composable
fun PlayButton(
    modifier: Modifier = Modifier,
    text: String = stringResource(R.string.play),
    onclick: () -> Unit
) {
    Button(
        onClick = onclick,
        modifier = modifier
            .fillMaxWidth()
            .height(BUTTON_HEIGHT),
        shape = MaterialTheme.shapes.small.copy(CornerSize(BUTTON_CORNER_SIZE)),
        colors = ButtonDefaults.buttonColors(backgroundColor = Orange75)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button.copy(
                color = Color.White,
                fontWeight = Bold
            )
        )
    }
}

@Preview
@Composable
fun HelloScreenPreview() {
    HelloScreen()
}
