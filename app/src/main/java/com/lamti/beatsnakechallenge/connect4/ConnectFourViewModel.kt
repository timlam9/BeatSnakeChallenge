package com.lamti.beatsnakechallenge.connect4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lamti.beatsnakechallenge.connect4.Event.OnColumnClicked
import com.lamti.beatsnakechallenge.connect4.Turn.Player

class ConnectFourViewModel : ViewModel() {

    var state: ConnectFourState by mutableStateOf(newGame())
        private set

    fun onEvent(event: Event) {
        state = when (event) {
            is OnColumnClicked -> {
                handleMove(event.index)
            }
        }
    }

    private fun newGame() = ConnectFourState(
        turn = Player,
        board = Board.generateEmptyBoard()
    )

    private fun updateGameStatus(board: Board): GameStatus {
        val width = board.columns.size
        val height = board.columns.first().slots.size
        for (j in 0 until width-3) {
            for (i in 0 until height){
                if (
                    board.columns[j].slots[i].availability == Availability.Player &&
                    board.columns[j+1].slots[i].availability == Availability.Player &&
                    board.columns[j+2].slots[i].availability == Availability.Player &&
                    board.columns[j+3].slots[i].availability == Availability.Player
                ) {
                    return GameStatus.PlayerWon
                }
            }
        }
        return GameStatus.Playing
    }

    private fun handleMove(columnIndex: Int): ConnectFourState {
        val (board, error) = try {
            Pair(state.board.update(columnIndex, state.turn), null)
        } catch (exception: ColumnAlreadyFilledException) {
            Pair(state.board, Error(exception.message))
        }
        val gameStatus = updateGameStatus(board)
        val turn = if (error == null && gameStatus == GameStatus.Playing) state.turn.next() else state.turn

        return state.copy(
            board = board,
            turn = turn,
            error = error,
            gameStatus = gameStatus
        )
    }

}
