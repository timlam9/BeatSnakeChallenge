package com.lamti.beatsnakechallenge.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.ui.MediaPlayerManager
import com.lamti.beatsnakechallenge.ui.SnakePreferences
import com.lamti.beatsnakechallenge.ui.navigation.SnakeNavigation
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val mediaPlayerManager = MediaPlayerManager(this@MainActivity)
    private val preferences = SnakePreferences()

    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BeatSnakeChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    SnakeNavigation(
                        viewModel = viewModel,
                        preferences = preferences
                    )
                }
            }
        }

        preferences.initSharedPrefs(this@MainActivity)
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
