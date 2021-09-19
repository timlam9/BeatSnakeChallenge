package com.lamti.beatsnakechallenge.connect4.domain

import com.lamti.beatsnakechallenge.connect4.ui.ColumnAlreadyFilledException

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
                val emptySlot = column.slots.lastOrNull { it.availability == Availability.Available }
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
