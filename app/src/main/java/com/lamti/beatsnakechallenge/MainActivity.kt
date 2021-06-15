package com.lamti.beatsnakechallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    @ExperimentalFoundationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeatSnakeChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SnakeGrid(viewModel)
                }
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun SnakeGrid(viewModel: MainViewModel) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        viewModel.board.value.grid.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                row.forEach { cell ->
                    Cell(
                        modifier = Modifier.weight(1f),
                        color = viewModel.getCellColor(cell.x, cell.y)
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