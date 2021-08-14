package com.lamti.beatsnakechallenge.connect4

import com.lamti.beatsnakechallenge.connect4.Availability.Available
import com.lamti.beatsnakechallenge.connect4.Turn.*

data class ConnectFourState(
    val turn: Turn,
    val board: Board,
    val error: Error? = null,
    val gameStatus: GameStatus = GameStatus.Playing
)

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

data class Error(val message: String)

data class Board(val columns: List<Column>) {

    companion object {

        fun generateEmptyBoard(): Board {
            val columns = mutableListOf<Column>()
            repeat(7) {
                val slots = mutableListOf<Slot>()
                repeat(6) { slot ->
                    slots.add(Slot(index = slot))
                }
                columns.add(Column(slots = slots))
            }
            return Board(columns = columns)
        }

    }

    fun update(updatedSlot: Int, turn: Turn): Board = Board(
        columns = columns.mapIndexed { index, column ->
            if (index == updatedSlot) {
                val emptySlot = column.slots.lastOrNull { it.availability == Available }
                if (emptySlot == null) {
                    throw ColumnAlreadyFilledException
                } else {
                    column.copy(slots = column.slots.map { if (it == emptySlot) it.claim(turn) else it })
                }
            } else
                column
        }
    )

}

object ColumnAlreadyFilledException : Exception() {

    override val message: String
        get() = "Cannot select an already filled board"

}

data class Column(val slots: List<Slot>)

data class Slot(
    val index: Int,
    val availability: Availability = Available
) {

    fun claim(turn: Turn): Slot = when (turn) {
        Player -> Slot(index = index, availability = Availability.Player)
        Opponent -> Slot(index = index, availability = Availability.Opponent)
    }

}

enum class Availability {

    Available,
    Player,
    Opponent

}

enum class Turn {

    Player,
    Opponent;

    override fun toString(): String {
        return "$name's turn!"
    }

    fun next(): Turn = when (this) {
        Player -> Opponent
        Opponent -> Player
    }

}
