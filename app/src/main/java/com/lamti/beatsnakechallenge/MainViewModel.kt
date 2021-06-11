package com.lamti.beatsnakechallenge

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.compose.ui.graphics.Color as ComposeColor

class MainViewModel : ViewModel() {

    private val board: Board = Board(11, 9)
    var boardState: MutableState<Board> = mutableStateOf(board)

    fun getCellColor(index: Int): ComposeColor =
        when (board.getCell(index).type) {
            Cell.Type.Empty -> ComposeColor.White
            Cell.Type.Passenger -> ComposeColor.Cyan
            Cell.Type.DriverHead -> ComposeColor.Black
            Cell.Type.DriverBody -> ComposeColor.Magenta
        }

}
