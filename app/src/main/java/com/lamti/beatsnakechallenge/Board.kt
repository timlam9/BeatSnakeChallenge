package com.lamti.beatsnakechallenge

import com.lamti.beatsnakechallenge.Cell.Type.DriverHead
import com.lamti.beatsnakechallenge.Cell.Type.Empty

data class Board(
    val grid: List<List<Cell>>
)

class Game(private val height: Int, private val width: Int) {

    fun generateGrid(): List<List<Cell>> =
        List(height) { y ->
            List(width) { x ->
                generateCell(x, y)
            }
        }

    private fun generateCell(x: Int, y: Int): Cell {
        val centerX = width / 2
        val centerY = height / 2

        return if (x == centerX && centerY == y) {
            Cell(DriverHead, x, y)
        } else {
            Cell(Empty, x, y)
        }
    }

}
