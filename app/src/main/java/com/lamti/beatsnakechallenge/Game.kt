package com.lamti.beatsnakechallenge

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Game(private val height: Int, private val width: Int) {

    private val _board: MutableStateFlow<Board> = MutableStateFlow(
        Board(
            grid = generateGrid(),
            driver = generateDriver(),
            passenger = generatePassenger(),
        )
    )

    val board: StateFlow<Board> = _board

    fun update() {
        val driverHead: Point = board.value.driver.head

        val futureDriverX = when {
            _board.value.direction == Board.Direction.Right && driverHead.x + 1 >= width -> 0
            _board.value.direction == Board.Direction.Left && driverHead.x == 0 -> width - 1
            _board.value.direction == Board.Direction.Left -> driverHead.x - 1
            _board.value.direction == Board.Direction.Right -> driverHead.x + 1
            else -> driverHead.x
        }

        val futureDriverY = when {
            _board.value.direction == Board.Direction.Down && driverHead.y + 1 >= height -> 0
            _board.value.direction == Board.Direction.Up && driverHead.y == 0 -> height - 1
            _board.value.direction == Board.Direction.Up -> driverHead.y - 1
            _board.value.direction == Board.Direction.Down -> driverHead.y + 1
            else -> driverHead.y
        }

        val futureDriverHead = Point(
            x = futureDriverX,
            y = futureDriverY
        )
        _board.value = _board.value.copy(
            driver = _board.value.driver.copy(head = futureDriverHead),
            passenger = updatePassenger(futureDriverHead)
        )

    }

    fun changeDirection() {
        _board.value = _board.value.copy(direction = _board.value.direction.nextItem())
    }

    private fun generateGrid(): List<List<Point>> =
        List(height) { y ->
            List(width) { x ->
                Point(x, y)
            }
        }

    private fun generatePassenger(driverHead: Point = Point(width / 2, height / 2)): Point =
        Point(
            x = (0 until width).filterNot { it == driverHead.x }.random(),
            y = (0 until height).filterNot { it == driverHead.y }.random()
        )

    private fun generateDriver(): Driver = Driver(
        head = Point(width / 2, height / 2),
        body = emptyList()
    )

    private fun updatePassenger(futureDriverHead: Point) =
        if (_board.value.passenger == futureDriverHead)
            generatePassenger(futureDriverHead)
        else
            _board.value.passenger

}
