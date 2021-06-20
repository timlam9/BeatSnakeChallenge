package com.lamti.beatsnakechallenge

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Game(private val height: Int, private val width: Int) {

    private val _running: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val running: StateFlow<Boolean> = _running

    private val _board: MutableStateFlow<Board> = MutableStateFlow(
        Board(
            grid = generateGrid(),
            driver = generateDriver(),
            passenger = generateInitialPassenger(),
        )
    )
    val board: StateFlow<Board> = _board

    private val _score: MutableStateFlow<Int> = MutableStateFlow(0)
    val score: StateFlow<Int> = _score

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

        val willBoardPassenger = futureDriverHead == _board.value.passenger
        val previousBody = _board.value.driver.body
        val body: List<Point> = previousBody.mapIndexed { index, _ ->
            if (index == 0) {
                _board.value.driver.head
            } else {
                previousBody[index - 1]
            }
        }

        val updatedBody = if (willBoardPassenger) {
            _score.value = _score.value + 1
            if (previousBody.isEmpty()) listOf(driverHead) else body + previousBody.last()
        } else {
            body
        }

        val updatedDriver = Driver(
            head = futureDriverHead,
            body = updatedBody
        )

        if (updatedBody.any { it == futureDriverHead }) {
            _running.value = false
            _score.value = 0
            Log.d("GAME_STATE", "Crash: ${_running.value}")
        }

        _board.value = _board.value.copy(
            driver = updatedDriver,
            passenger = updatePassenger(updatedDriver)
        )
    }

    fun changeDirection(direction: Board.Direction) {
        _board.value = _board.value.copy(direction = direction)
    }

    private fun generateGrid(): List<List<Point>> = List(height) { y ->
        List(width) { x ->
            Point(x, y)
        }
    }

    private fun generateInitialPassenger(driverHead: Point = Point(width / 2, height / 2)): Point =
        Point(
            x = (0 until width).filterNot { it == driverHead.x }.random(),
            y = (0 until height).filterNot { it == driverHead.y }.random()
        )

    private fun generatePassenger(driver: Driver): Point = _board.value.grid
        .flatten()
        .shuffled()
        .first { point ->
            (driver.body + driver.head).none { it == point }
        }

    private fun generateDriver(): Driver = Driver(
        head = Point(width / 2, height / 2),
        body = emptyList()
    )

    private fun updatePassenger(futureDriver: Driver) =
        if (_board.value.passenger == futureDriver.head)
            generatePassenger(futureDriver)
        else
            _board.value.passenger

    fun resetGame() {
        _board.value = Board(
            grid = generateGrid(),
            driver = generateDriver(),
            passenger = generateInitialPassenger(),
        )
        _running.value = true
        Log.d("GAME_STATE", "Start game: ${_running.value}")
    }

}
