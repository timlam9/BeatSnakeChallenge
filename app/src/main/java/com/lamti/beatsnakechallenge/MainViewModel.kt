package com.lamti.beatsnakechallenge

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow
import androidx.compose.ui.graphics.Color as ComposeColor

class MainViewModel : ViewModel() {

    companion object {

        private const val HEIGHT = 11
        private const val WIDTH = 9

    }

    private val game = Game(HEIGHT, WIDTH)
    val board: StateFlow<Board> = game.board

    fun getCellColor(x: Int, y: Int): ComposeColor =
        when (game.board.value.grid[y][x].type) {
            Point.Type.Empty -> ComposeColor.White
            Point.Type.Passenger -> ComposeColor.Cyan
            Point.Type.DriverHead -> ComposeColor.Black
            Point.Type.DriverBody -> ComposeColor.Magenta
        }

    fun update() {
        game.update()
    }

    fun changeDirection() {
        game.changeDirection()
    }

}
