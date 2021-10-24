package com.lamti.beatsnakechallenge.connect4.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.beatsnakechallenge.connect4.data.GameOverStatus
import com.lamti.beatsnakechallenge.connect4.data.SocketMessage
import com.lamti.beatsnakechallenge.connect4.data.SocketMessage.Disconnected
import com.lamti.beatsnakechallenge.connect4.data.SocketMessage.Move
import com.lamti.beatsnakechallenge.connect4.data.WebSocket
import com.lamti.beatsnakechallenge.connect4.domain.Board
import com.lamti.beatsnakechallenge.connect4.domain.ConnectFourState
import com.lamti.beatsnakechallenge.connect4.domain.Error
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.Playing
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.SearchingOpponent
import com.lamti.beatsnakechallenge.connect4.domain.Turn
import com.lamti.beatsnakechallenge.connect4.domain.Turn.Opponent
import com.lamti.beatsnakechallenge.connect4.domain.Turn.Player
import com.lamti.beatsnakechallenge.connect4.ui.Event.OnColumnClicked
import com.lamti.beatsnakechallenge.connect4.ui.Event.OnRestartClicked
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
        webSocket.messages.onEach(::handleSocketMessages).launchIn(viewModelScope)
    }

    override fun onCleared() {
        super.onCleared()
        webSocket.sendMessage(Disconnected(userID))
        webSocket.closeSocket()
    }

    private fun handleSocketMessages(message: SocketMessage) {
        when (message) {
            is SocketMessage.StartTurn -> {
                state = state.copy(
                    board = message.board,
                    turn = message.turn,
                    gameStatus = Playing,
                    error = null
                )
                userID = message.userID
            }
            is SocketMessage.PlayerTurn -> state = state.copy(
                board = message.board,
                turn = message.turn,
                error = null
            )
            is SocketMessage.GameOver -> state = state.copy(
                board = message.board,
                gameStatus = message.winner.toGameStatus(message.turn),
                error = null
            )
            is SocketMessage.SocketError -> setErrorState(message)
            is Move -> Unit
            is Disconnected -> Unit
        }
    }

    private fun setErrorState(message: SocketMessage.SocketError) {
        val errorMessage = when (message.errorType) {
            "ColumnAlreadyFilled" -> "Cannot select an already filled board"
            "ConnectionLost" -> "Connection lost"
            else -> "Some error occurred"
        }
        state = state.copy(
            error = Error(errorMessage),
            gameStatus = if (message.errorType == "ConnectionLost") GameStatus.Disconnected else Playing
        )
    }

    fun onEvent(event: Event) {
        when (event) {
            is OnColumnClicked -> {
                if (state.turn == Opponent) return
                webSocket.sendMessage(Move(userID, event.index))
            }
            OnRestartClicked -> {
                webSocket.restart(viewModelScope)
                state = state.copy(gameStatus = SearchingOpponent)
            }
        }
    }

}

private fun GameOverStatus.toGameStatus(turn: Turn): GameStatus = when (this) {
    GameOverStatus.Won -> when (turn) {
        Player -> GameStatus.OpponentWon
        Opponent -> GameStatus.PlayerWon
    }
    GameOverStatus.Draw -> GameStatus.Draw
}

