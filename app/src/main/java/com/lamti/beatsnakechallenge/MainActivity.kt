package com.lamti.beatsnakechallenge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    companion object {

        private const val SPEED: Long = 250

    }

    private val viewModel: MainViewModel by viewModels()

    @ExperimentalMaterialApi
    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeatSnakeChallengeTheme {
                LaunchedEffect(key1 = Unit) {
                    while (isActive) {
                        delay(SPEED)
                        viewModel.update()
                    }
                }
                Surface(color = MaterialTheme.colors.background) {
                    Column(modifier = Modifier.fillMaxSize()) {
                        SnakeGrid(viewModel)
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(
                                modifier = Modifier.size(width = 300.dp, height = 200.dp),
                                onClick = {
                                    viewModel.changeDirection()
                                },
                                colors = ButtonDefaults.textButtonColors(backgroundColor = Color.Blue)
                            ) {
                                Text(
                                    text = "Change direction",
                                    fontSize = 30.sp,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@ExperimentalFoundationApi
@Composable
fun SnakeGrid(viewModel: MainViewModel) {
    val board = viewModel.board.collectAsState().value

    Column {
        board.grid.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { cell ->
                    Cell(
                        modifier = Modifier.weight(1f),
                        color = when (cell) {
                            in board.driver.body -> Color.Magenta
                            board.driver.head -> Color.Black
                            board.passenger -> Color.Cyan
                            else -> Color.White
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun Cell(modifier: Modifier, color: Color = Color.White) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_beat),
            contentDescription = null
        )
    }
}

@ExperimentalFoundationApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    BeatSnakeChallengeTheme {
        SnakeGrid(MainViewModel())
    }
}