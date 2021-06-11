package com.lamti.beatsnakechallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
    LazyVerticalGrid(
        cells = GridCells.Fixed(viewModel.boardWidth),
        modifier = Modifier.fillMaxSize()
    ) {
        Log.d("TEST_", "Size: ${viewModel.boardState.value.flatten().size}")
        items(viewModel.boardSize) { index: Int ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .background(viewModel.getCellColor(index))
            ) {
                Image(painter = painterResource(id = R.drawable.ic_beat), contentDescription = null)
            }
        }
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