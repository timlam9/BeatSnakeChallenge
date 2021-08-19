package com.lamti.beatsnakechallenge.connect4

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.lamti.beatsnakechallenge.connect4.Event.OnColumnClicked
import com.lamti.beatsnakechallenge.connect4.GameStatus.Draw
import com.lamti.beatsnakechallenge.connect4.GameStatus.OpponentWon
import com.lamti.beatsnakechallenge.connect4.GameStatus.PlayerWon
import com.lamti.beatsnakechallenge.connect4.GameStatus.Playing
import com.lamti.beatsnakechallenge.connect4.Turn.Player

class ConnectFourViewModel : ViewModel() {

    var state: ConnectFourState by mutableStateOf(newGame())
        private set

    fun onEvent(event: Event) {
        state = when (event) {
            is OnColumnClicked -> handleMove(event.index)
            Event.OnRestartClicked -> newGame()
        }
    }

    private fun newGame() = ConnectFourState(
        turn = Player,
        board = Board.generateEmptyBoard()
    )

    private fun updateGameStatus(board: Board): GameStatus {
        val width = board.columns.size
        val height = board.columns.first().slots.size

        // Horizontally score 4
        for (columnIndex in 0 until width - 3) {
            for (rowIndex in 0 until height) {
                if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Player &&
                    board.columns[columnIndex + 1].slots[rowIndex].availability == Availability.Player &&
                    board.columns[columnIndex + 2].slots[rowIndex].availability == Availability.Player &&
                    board.columns[columnIndex + 3].slots[rowIndex].availability == Availability.Player
                ) {
                    return PlayerWon
                } else if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Opponent &&
                    board.columns[columnIndex + 1].slots[rowIndex].availability == Availability.Opponent &&
                    board.columns[columnIndex + 2].slots[rowIndex].availability == Availability.Opponent &&
                    board.columns[columnIndex + 3].slots[rowIndex].availability == Availability.Opponent
                ) {
                    return OpponentWon
                }
            }
        }

        // Vertically score 4
        for (rowIndex in 0 until height - 3) {
            for (columnIndex in 0 until width) {
                if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Opponent &&
                    board.columns[columnIndex].slots[rowIndex + 1].availability == Availability.Opponent &&
                    board.columns[columnIndex].slots[rowIndex + 2].availability == Availability.Opponent &&
                    board.columns[columnIndex].slots[rowIndex + 3].availability == Availability.Opponent
                ) {
                    return OpponentWon
                } else if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Player &&
                    board.columns[columnIndex].slots[rowIndex + 1].availability == Availability.Player &&
                    board.columns[columnIndex].slots[rowIndex + 2].availability == Availability.Player &&
                    board.columns[columnIndex].slots[rowIndex + 3].availability == Availability.Player
                ) {
                    return PlayerWon
                }
            }
        }

        // Ascending diagonally score 4
        for (columnIndex in 3 until width) {
            for (rowIndex in 0 until height - 3) {
                if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Player &&
                    board.columns[columnIndex - 1].slots[rowIndex + 1].availability == Availability.Player &&
                    board.columns[columnIndex - 2].slots[rowIndex + 2].availability == Availability.Player &&
                    board.columns[columnIndex - 3].slots[rowIndex + 3].availability == Availability.Player
                ) {
                    return PlayerWon
                } else if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Opponent &&
                    board.columns[columnIndex - 1].slots[rowIndex + 1].availability == Availability.Opponent &&
                    board.columns[columnIndex - 2].slots[rowIndex + 2].availability == Availability.Opponent &&
                    board.columns[columnIndex - 3].slots[rowIndex + 3].availability == Availability.Opponent
                ) {
                    return OpponentWon
                }
            }
        }

        // Descending diagonally score 4
        for (columnIndex in 3 until width) {
            for (rowIndex in 3 until height) {
                if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Player &&
                    board.columns[columnIndex - 1].slots[rowIndex - 1].availability == Availability.Player &&
                    board.columns[columnIndex - 2].slots[rowIndex - 2].availability == Availability.Player &&
                    board.columns[columnIndex - 3].slots[rowIndex - 3].availability == Availability.Player
                ) {
                    return PlayerWon
                } else if (
                    board.columns[columnIndex].slots[rowIndex].availability == Availability.Opponent &&
                    board.columns[columnIndex - 1].slots[rowIndex - 1].availability == Availability.Opponent &&
                    board.columns[columnIndex - 2].slots[rowIndex - 2].availability == Availability.Opponent &&
                    board.columns[columnIndex - 3].slots[rowIndex - 3].availability == Availability.Opponent
                ) {
                    return OpponentWon
                }
            }
        }

        // Draw
        if (
            board.columns.all { column ->
                column.slots.all { slot ->
                    slot.availability != Availability.Available
                }
            }
        ) {
            return Draw
        }

        return Playing
    }

    private fun handleMove(columnIndex: Int): ConnectFourState {
        val (board, error) = try {
            Pair(state.board.update(columnIndex, state.turn), null)
        } catch (exception: ColumnAlreadyFilledException) {
            Pair(state.board, Error(exception.message))
        }
        val gameStatus = updateGameStatus(board)
        val turn = if (error == null && gameStatus == Playing) state.turn.next() else state.turn

        return state.copy(
            board = board,
            turn = turn,
            error = error,
            gameStatus = gameStatus
        )
    }

}
