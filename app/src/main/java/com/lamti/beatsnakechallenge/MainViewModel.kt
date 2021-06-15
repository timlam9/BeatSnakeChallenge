package com.lamti.beatsnakechallenge

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color as ComposeColor

class MainViewModel : ViewModel() {

    companion object {

        private const val HEIGHT = 11
        private const val WIDTH = 9

    }

    private val game = Game(HEIGHT, WIDTH)

    private val _board: MutableState<Board> = mutableStateOf(Board(game.generateGrid()))
    val board: State<Board> = _board

    fun getCellColor(x: Int, y: Int): ComposeColor =
        when (board.value.grid[y][x].type) {
            Cell.Type.Empty -> ComposeColor.White
            Cell.Type.Passenger -> ComposeColor.Cyan
            Cell.Type.DriverHead -> ComposeColor.Black
            Cell.Type.DriverBody -> ComposeColor.Magenta
        }

}
