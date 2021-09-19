package com.lamti.beatsnakechallenge.connect4.domain

enum class GameStatus {

    Playing,
    PlayerWon,
    OpponentWon,
    Draw;

    fun getMessage(): String = when (this) {
        Playing -> "Playing..."
        PlayerWon -> "Player won!"
        OpponentWon -> "Opponent won!"
        Draw -> "Draw!"
    }

}
