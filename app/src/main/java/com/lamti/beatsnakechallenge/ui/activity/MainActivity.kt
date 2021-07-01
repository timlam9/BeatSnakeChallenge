package com.lamti.beatsnakechallenge.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.domain.SnakeControllers
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel.Companion.SPEED
import com.lamti.beatsnakechallenge.ui.components.Joystick
import com.lamti.beatsnakechallenge.ui.components.PieController
import com.lamti.beatsnakechallenge.ui.components.Score
import com.lamti.beatsnakechallenge.ui.components.SnakeBoard
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme
import com.lamti.beatsnakechallenge.ui.theme.Navy100
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    companion object {

        const val CRASH_ANIMATION_DURATION = 300

    }

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            BeatSnakeChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    Snake(viewModel = viewModel)
                }
            }
        }

        updateGameLoop()
    }

    private fun updateGameLoop() {
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
    private fun Snake(viewModel: MainViewModel) {
        val board by viewModel.board.collectAsState()
        val score by viewModel.score.collectAsState()
        val controllers = viewModel.controllers

        val animatedColor = crashAnimatedColor()

        Column(modifier = Modifier.fillMaxSize()) {
            Score(score = score.toString()) {
                viewModel.onSettingsClicked()
            }
            SnakeBoard(board = board) { point ->
                when {
                    board.driver.hasCrashed() && board.driver.head == point -> animatedColor.value
                    else -> viewModel.colorCell(point)
                }
            }
            when (controllers) {
                SnakeControllers.PieController -> {
                    PieController(
                        onUpClick = { viewModel.changeDirection(Board.Direction.Up) },
                        onLeftClick = { viewModel.changeDirection(Board.Direction.Left) },
                        onRightClick = { viewModel.changeDirection(Board.Direction.Right) },
                        onDownClick = { viewModel.changeDirection(Board.Direction.Down) }
                    )
                }
                SnakeControllers.Joystick -> {
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
    }

    @Composable
    private fun crashAnimatedColor(): State<Color> {
        val infiniteTransition = rememberInfiniteTransition()
        return infiniteTransition.animateColor(
            initialValue = Color.Red,
            targetValue = Navy100,
            animationSpec = infiniteRepeatable(
                animation = tween(
                    durationMillis = CRASH_ANIMATION_DURATION,
                    easing = FastOutSlowInEasing
                ),
                repeatMode = RepeatMode.Reverse
            )
        )
    }

}
