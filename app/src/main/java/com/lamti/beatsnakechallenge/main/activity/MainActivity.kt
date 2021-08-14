package com.lamti.beatsnakechallenge.main.activity

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.snake.data.SnakeRepository
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.Game
import com.lamti.beatsnakechallenge.snake.ui.MediaPlayerManager
import com.lamti.beatsnakechallenge.snake.ui.SnakePreferences
import com.lamti.beatsnakechallenge.main.activity.MainViewModel.Companion.HEIGHT
import com.lamti.beatsnakechallenge.main.activity.MainViewModel.Companion.WIDTH
import com.lamti.beatsnakechallenge.main.navigation.BeatGamesNavigation
import com.lamti.beatsnakechallenge.main.theme.BeatGamesTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    private val mediaPlayerManager = MediaPlayerManager(this@MainActivity)

    private val repository by lazy {
        SnakeRepository(
            SnakePreferences(
                applicationContext.getSharedPreferences(
                    SnakePreferences.SNAKE_PREFERENCES,
                    Context.MODE_PRIVATE
                )
            )
        )
    }

    private val viewModelFactory by lazy {
        MainViewModelFactory(
            repository = repository,
            game = Game(HEIGHT, WIDTH)
        )
    }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this, viewModelFactory).get(MainViewModel::class.java)
    }

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeatGamesTheme {
                Surface(color = MaterialTheme.colors.surface) {
                    MainActivityContent(viewModel = viewModel)
                }
            }
        }

        updateGameLoop()
        handleSounds()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayerManager.resumeBackgroundMusic()
    }

    override fun onPause() {
        super.onPause()
        mediaPlayerManager.pauseBackgroundMusic()
    }

    private fun updateGameLoop() {
        lifecycleScope.launchWhenResumed {
            while (isActive) {
                delay(viewModel.snakeSpeed.value)
                if (viewModel.running.value) {
                    Log.d("GAME_STATE", "Running: ${viewModel.running.value}")
                    viewModel.updateGame()
                }
            }
        }
    }

    private fun handleSounds() {
        mediaPlayerManager.initMediaPlayers()
        viewModel.score.onEach(mediaPlayerManager::playBoardPassengerSound).launchIn(lifecycleScope)
        viewModel.board.onEach(::playGameOverSound).launchIn(lifecycleScope)
    }

    private fun playGameOverSound(board: Board) {
        if (board.driver.hasCrashed()) mediaPlayerManager.playGameOverSound()
    }

}

@ExperimentalAnimationApi
@Composable
fun MainActivityContent(viewModel: MainViewModel) {
    val user by viewModel.user.collectAsState()
    val board by viewModel.board.collectAsState()
    val score by viewModel.score.collectAsState()

    BeatGamesNavigation(
        user = user,
        users = viewModel.users,
        uploadUser = { viewModel.uploadUser(it) },
        onSettingsClicked = { viewModel.onSettingsClicked() },
        onHighscoresClicked = {
            viewModel.getUsers()
            viewModel.closeSettingsDialog()
        },
        currentSnakeSpeed = viewModel.snakeSpeed,
        currentController = viewModel.controllers,
        onSpeedChanged = { viewModel.changeSnakeSpeed(it) },
        onControllerChanged = { viewModel.setController(it) },
        board = board,
        score = score,
        showSettings = viewModel.showSettings,
        onChangeDirection = { viewModel.changeDirection(it) },
        restartGame = { viewModel.restartGame() },
        gameOver = { viewModel.gameOver(it) }
    ) { viewModel.colorCell(it) }
}
