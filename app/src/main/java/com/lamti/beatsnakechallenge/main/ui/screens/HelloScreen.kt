package com.lamti.beatsnakechallenge.main.ui.screens

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
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.snake.ui.BUTTON_CORNER_SIZE
import com.lamti.beatsnakechallenge.snake.ui.BUTTON_HEIGHT
import com.lamti.beatsnakechallenge.main.ui.theme.Navy50
import com.lamti.beatsnakechallenge.main.ui.theme.Orange75

@Composable
fun HelloScreen(
    onPlayClicked: (
        name: String,
        email: String,
        password: String
    ) -> Unit
) {
    val context = LocalContext.current
    var name by rememberSaveable { mutableStateOf("") }
    var email by rememberSaveable { mutableStateOf("") }
    var password by rememberSaveable { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {
        Spacer(modifier = Modifier.weight(0.5f))
        Text(
            text = stringResource(R.string.hello_there),
            style = MaterialTheme.typography.h4.copy(
                color = MaterialTheme.colors.background,
                fontWeight = Bold
            )
        )
        Text(
            text = stringResource(R.string.welcome_to_beat_games),
            style = MaterialTheme.typography.h5.copy(color = Navy50)
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(
            text = stringResource(R.string.whats_your_name),
            style = MaterialTheme.typography.h5.copy(color = Navy50)
        )
        InfoTextField(text = name) {
            name = it
        }
        InfoTextField(
            text = email,
            label = stringResource(R.string.type_email),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Email
            )
        ) {
            email = it
        }
        PasswordTextField(text = password) {
            password = it
        }
        Spacer(modifier = Modifier.weight(4f))
        PlayButton {
            when {
                name.isEmpty() -> Toast(context).apply {
                    setText("Please type your name first.")
                    show()
                }
                email.isEmpty() -> Toast(context).apply {
                    setText("Please type your email first.")
                    show()
                }
                password.isEmpty() -> Toast(context).apply {
                    setText("Please type your password first.")
                    show()
                }
                else -> onPlayClicked(name, email, password)
            }
        }
    }
}

@Composable
fun InfoTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String = stringResource(R.string.type_your_name),
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(
        imeAction = ImeAction.Next,
        keyboardType = KeyboardType.Text,
        capitalization = KeyboardCapitalization.Words
    ),
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
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(onNext = { focusManager.moveFocus(FocusDirection.Down) })
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

@Composable
fun PasswordTextField(
    modifier: Modifier = Modifier,
    text: String,
    label: String = stringResource(R.string.type_password),
    placeholder: String = stringResource(R.string.password),
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        value = text,
        onValueChange = { onValueChange(it) },
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth(),
        label = { Text(label) },
        placeholder = { Text(placeholder) },
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
        singleLine = true,
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation(),
        trailingIcon = {
            val iconId = if (passwordVisibility) R.drawable.ic_visibility else R.drawable.ic_visibility_off
            IconButton(
                onClick = { passwordVisibility = !passwordVisibility }
            ) {
                Icon(painter = painterResource(iconId), "")
            }
        }
    )
}

@Preview
@Composable
fun HelloScreenPreview() {
    HelloScreen { _: String, _: String, _: String -> }
}
