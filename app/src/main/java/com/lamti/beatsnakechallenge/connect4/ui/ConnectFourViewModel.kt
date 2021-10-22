package com.lamti.beatsnakechallenge.connect4.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.beatsnakechallenge.connect4.data.GameOverStatus
import com.lamti.beatsnakechallenge.connect4.data.SocketMessage
import com.lamti.beatsnakechallenge.connect4.data.SocketMessage.Move
import com.lamti.beatsnakechallenge.connect4.data.WebSocket
import com.lamti.beatsnakechallenge.connect4.domain.Board
import com.lamti.beatsnakechallenge.connect4.domain.ConnectFourState
import com.lamti.beatsnakechallenge.connect4.domain.Error
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.Playing
import com.lamti.beatsnakechallenge.connect4.domain.Turn
import com.lamti.beatsnakechallenge.connect4.domain.Turn.Opponent
import com.lamti.beatsnakechallenge.connect4.domain.Turn.Player
import com.lamti.beatsnakechallenge.connect4.ui.Event.OnColumnClicked
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ConnectFourViewModel : ViewModel() {

    private val webSocket = WebSocket()
    private var userID = ""

    var state: ConnectFourState by mutableStateOf(
        ConnectFourState(
            turn = Player,
            board = Board.generateEmptyBoard()
        )
    )
        private set

    init {
        webSocket.start(viewModelScope)
        webSocket.messages.onEach {
            when (val message = it) {
                is SocketMessage.StartTurn -> {
                    state = state.copy(
                        board = message.board,
                        turn = message.turn,
                        gameStatus = Playing
                    )
                    userID = message.userID
                }
                is Move -> Unit
                is SocketMessage.PlayerTurn -> {
                    state = state.copy(
                        board = message.board,
                        turn = message.turn
                    )
                }
                is SocketMessage.GameOver -> {
                    state = state.copy(
                        board = message.board,
                        gameStatus = message.winner.toGameStatus(message.turn)
                    )
                }
            }
        }.launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        webSocket.closeSocket()
    }

    fun onEvent(event: Event) {
        when (event) {
            is OnColumnClicked -> {
                if (state.turn == Opponent) return
                webSocket.sendMessage(Move(userID, event.index))
            }
            Event.OnRestartClicked -> Unit
        }
    }

    private fun handleMove(columnIndex: Int) {
        val (board, error) = try {
            Pair(state.board.update(columnIndex, state.turn), null)
        } catch (exception: ColumnAlreadyFilledException) {
            Pair(state.board, Error(ColumnAlreadyFilledException.message))
        }
    }

}

private fun GameOverStatus.toGameStatus(turn: Turn): GameStatus = when (this) {
    GameOverStatus.Won -> when(turn) {
        Player -> GameStatus.OpponentWon
        Opponent -> GameStatus.PlayerWon
    }
    GameOverStatus.Draw -> GameStatus.Draw
}

