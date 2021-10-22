package com.lamti.beatsnakechallenge.connect4.domain

data class ConnectFourState(
    val turn: Turn,
    val board: Board,
    val error: Error? = null,
    val gameStatus: GameStatus = GameStatus.SearchingOpponent
)
