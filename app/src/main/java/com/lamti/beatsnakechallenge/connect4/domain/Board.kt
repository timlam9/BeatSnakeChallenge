package com.lamti.beatsnakechallenge.connect4.domain

import kotlinx.serialization.Serializable

@Serializable
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

}
