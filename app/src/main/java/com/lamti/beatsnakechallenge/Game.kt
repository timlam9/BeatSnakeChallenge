package com.lamti.beatsnakechallenge

import com.lamti.beatsnakechallenge.Point.Type.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Game(private val height: Int, private val width: Int) {

    private val _board: MutableStateFlow<Board> = MutableStateFlow(Board(generateGrid()))
    val board: StateFlow<Board> = _board

    private fun generateGrid(): List<List<Point>> =
        List(height) { y ->
            List(width) { x ->
                generateCell(x, y)
            }
        }

    private fun generateCell(x: Int, y: Int): Point {
        val centerX = width / 2
        val centerY = height / 2

        val firstPassengerX = (0 until width).random()
        val firstPassengerY = (0 until height).random()

        return if (x == centerX && centerY == y) {
            Point(DriverHead, x, y)
        } else if (x == firstPassengerX && y == firstPassengerY) {
            Point(Passenger, x, y)
        } else {
            Point(Empty, x, y)
        }
    }

    fun update() {
        var driverX = 0
        var driverY = 0

        board.value.grid.forEach { rows ->
            rows.forEach { cell ->
                if (cell.type == DriverHead) {
                    driverX = cell.x
                    driverY = cell.y
                }
            }
        }

        val futureDriverX = when {
            _board.value.direction == Board.Direction.Right && driverX + 1 >= width -> 0
            _board.value.direction == Board.Direction.Left && driverX == 0 -> width - 1
            _board.value.direction == Board.Direction.Left -> driverX - 1
            _board.value.direction == Board.Direction.Right -> driverX + 1
            else -> driverX
        }

        val futureDriverY = when {
            _board.value.direction == Board.Direction.Down && driverY + 1 >= height -> 0
            _board.value.direction == Board.Direction.Up && driverY == 0 -> height - 1
            _board.value.direction == Board.Direction.Up -> driverY - 1
            _board.value.direction == Board.Direction.Down -> driverY + 1
            else -> driverY
        }

        _board.value = _board.value.copy(
            grid = updateGrid(
                currentDriver = Point(Empty, driverX, driverY),
                updatedDriver = Point(DriverHead, futureDriverX, futureDriverY)
            )
        )
    }

    private fun updateGrid(
        currentDriver: Point,
        updatedDriver: Point
    ): List<List<Point>> =
        _board.value.grid.mapIndexed { y, rows ->
            rows.mapIndexed { x, cell ->
                when {
                    currentDriver.x == x && currentDriver.y == y -> Point(Empty, x, y)
                    updatedDriver.x == x && updatedDriver.y == y -> Point(DriverHead, x, y)
                    else -> cell
                }
            }
        }

    fun changeDirection() {
        _board.value = _board.value.copy(direction = _board.value.direction.nextItem())
    }

}
