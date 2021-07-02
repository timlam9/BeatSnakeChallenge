package com.lamti.beatsnakechallenge.ui.activity

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.ui.MediaPlayerManager
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel.Companion.SPEED
import com.lamti.beatsnakechallenge.ui.components.Snake
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    companion object {

        const val CRASH_ANIMATION_DURATION = 300

    }

    private val viewModel: MainViewModel by viewModels()
    private val mediaPlayerManager = MediaPlayerManager(this@MainActivity)

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
                delay(SPEED)
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
