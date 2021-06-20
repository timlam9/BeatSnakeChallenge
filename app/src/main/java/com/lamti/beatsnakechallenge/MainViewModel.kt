package com.lamti.beatsnakechallenge

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import com.lamti.beatsnakechallenge.ui.theme.Mint100
import com.lamti.beatsnakechallenge.ui.theme.MintBlend14
import com.lamti.beatsnakechallenge.ui.theme.Navy100
import kotlinx.coroutines.flow.StateFlow

class MainViewModel : ViewModel() {

    companion object {

        private const val HEIGHT = 11
        private const val WIDTH = 9
        const val SPEED: Long = 300

    }

    private val game = Game(HEIGHT, WIDTH)

    val running: StateFlow<Boolean> = game.running
    val board: StateFlow<Board> = game.board
    val score: StateFlow<Int> = game.score

    fun colorCell(point: Point): Color = when (point) {
        in board.value.driver.body -> MintBlend14
        board.value.driver.head -> Navy100
        board.value.passenger -> Mint100
        else -> Color.White
    }

    fun changeDirection(direction: Board.Direction) {
        game.changeDirection(direction)
    }

    fun updateGame() {
        game.update()
    }

    fun restartGame() {
        if (!running.value) {
            game.resetGame()
        }
    }

}
