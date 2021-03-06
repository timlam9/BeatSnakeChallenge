package com.lamti.beatsnakechallenge.snake.ui

import android.content.Context
import android.media.MediaPlayer

class MediaPlayerManager(private val context: Context) {

    private lateinit var musicPlayer: MediaPlayer

    companion object {

        private const val BACKGROUND_MUSIC = "bg_music"
        private const val BOARD_PASSENGER = "board_passenger"
        private const val GAME_OVER = "crash_short"
        private const val RAW_FILE = "raw"

    }

    fun initMediaPlayers() {
        musicPlayer = setupPlayer(BACKGROUND_MUSIC)
        with(musicPlayer) {
            isLooping = true
            setVolume(500f, 500f)
            start()
        }
    }

    fun resumeBackgroundMusic() {
        musicPlayer.start()
    }

    fun pauseBackgroundMusic() {
        musicPlayer.pause()
    }

    fun playBoardPassengerSound(score: Int) {
        if (score == 0) return
        with(setupPlayer(BOARD_PASSENGER)) {
            setVolume(0.1f, 0.1f)
            start()
        }
    }

    fun playGameOverSound() {
        setupPlayer(GAME_OVER).start()
    }

    private fun setupPlayer(soundName: String): MediaPlayer {
        with(context) {
            val resID: Int = resources.getIdentifier(soundName, RAW_FILE, packageName)
            return MediaPlayer.create(this, resID)
        }
    }

}
