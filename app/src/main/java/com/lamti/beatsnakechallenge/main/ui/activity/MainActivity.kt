package com.lamti.beatsnakechallenge.main.ui.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.main.ui.navigation.BeatGamesNavigation
import com.lamti.beatsnakechallenge.main.ui.theme.BeatGamesTheme
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.ui.MediaPlayerManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var mediaPlayerManager: MediaPlayerManager
    private val viewModel: MainViewModel by viewModels()

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

    @SuppressLint("LogNotTimber")
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

@Composable
fun MainActivityContent(viewModel: MainViewModel) {
    val user by viewModel.user.collectAsState()
    val board by viewModel.board.collectAsState()
    val score by viewModel.score.collectAsState()

    BeatGamesNavigation(
        viewModel = viewModel,
        user = user,
        board = board,
        score = score,
    )
}
