package com.lamti.beatsnakechallenge.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel.Companion.SPEED
import com.lamti.beatsnakechallenge.ui.components.Joystick
import com.lamti.beatsnakechallenge.ui.components.Score
import com.lamti.beatsnakechallenge.ui.components.SnakeGrid
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateGameLooper()
        setContent {
            BeatSnakeChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent(viewModel = viewModel)
                }
            }
        }
    }

    private fun updateGameLooper() {
        lifecycleScope.launchWhenResumed {
            while (isActive) {
                delay(SPEED)
                if (viewModel.running.value) {
                    Log.d("GAME_STATE", "Running: ${viewModel.running.value}")
                    viewModel.updateGame()
                }
            }
        }
    }

    @Composable
    private fun MainContent(viewModel: MainViewModel) {
        val board by viewModel.board.collectAsState()
        val score by viewModel.score.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            Score(score = score.toString())
            SnakeGrid(board = board) { point ->
                viewModel.colorCell(point)
            }
            Joystick(
                onUpClick = { viewModel.changeDirection(Board.Direction.Up) },
                onLeftClick = { viewModel.changeDirection(Board.Direction.Left) },
                onRightClick = { viewModel.changeDirection(Board.Direction.Right) },
                onDownClick = { viewModel.changeDirection(Board.Direction.Down) },
                onCenterClick = { viewModel.restartGame() }
            )
        }
    }

}
