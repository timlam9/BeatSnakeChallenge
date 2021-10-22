package com.lamti.beatsnakechallenge.connect4.data

import com.lamti.beatsnakechallenge.connect4.domain.Board
import com.lamti.beatsnakechallenge.connect4.domain.Turn
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed class SocketMessage {

    @Serializable
    @SerialName("start_turn")
    data class StartTurn(val board: Board, val turn: Turn, val userID: String) : SocketMessage()

    @Serializable
    @SerialName("player_turn")
    data class PlayerTurn(val board: Board, val turn: Turn) : SocketMessage()

    @Serializable
    @SerialName("move")
    data class Move(val userID: String, val move: Int) : SocketMessage()

    @Serializable
    @SerialName("game_over")
    data class GameOver(val board: Board, val winner: GameOverStatus, val turn: Turn) : SocketMessage()

}

enum class GameOverStatus {
    Won,
    Draw
}
