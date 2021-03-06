package com.lamti.beatsnakechallenge.connect4.domain

enum class GameStatus {

    SearchingOpponent,
    Playing,
    PlayerWon,
    OpponentWon,
    Draw,
    Disconnected;

    fun getMessage(): String = when (this) {
        SearchingOpponent -> "Searching opponent"
        Playing -> "Playing..."
        PlayerWon -> "You won"
        OpponentWon -> "You lost"
        Draw -> "Draw"
        Disconnected -> "Game disconnected"
    }

}
