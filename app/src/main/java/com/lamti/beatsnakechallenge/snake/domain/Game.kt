package com.lamti.beatsnakechallenge.snake.domain

import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Down
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Left
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Right
import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Up
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class Game(
    private val height: Int,
    private val width: Int
) {

    companion object {

        private const val POINT_INCREMENT = 100

    }

    private val pointManager = PointManager(height = height, width = width)
    private val scoreManager = ScoreManager(increment = POINT_INCREMENT)

    private val _running: MutableStateFlow<Boolean> = MutableStateFlow(true)
    val running: StateFlow<Boolean> = _running

    private val _board: MutableStateFlow<Board> = MutableStateFlow(pointManager.generateInitialBoard())
    val board: StateFlow<Board> = _board

    val score: StateFlow<Int> = scoreManager.score

    var canChangeDirection = true

    fun update() {
        val futureDriver = _board.value.driver.update(
            _board.value.direction,
            _board.value.passenger,
            width,
            height
        )

        when {
            futureDriver.willBoardPassenger(_board.value.passenger) -> scoreManager.score()
            futureDriver.hasCrashed() -> _running.value = false
        }

        _board.value = _board.value.copy(
            driver = futureDriver,
            passenger = updatePassenger(futureDriver)
        )
        canChangeDirection = true
    }

    fun changeDirection(direction: Board.Direction) {
        if (!canChangeDirection) return

        val previousDirection = _board.value.direction
        when (direction) {
            Right -> if (previousDirection == Left) return
            Down -> if (previousDirection == Up) return
            Left -> if (previousDirection == Right) return
            Up -> if (previousDirection == Down) return
        }
        _board.value = _board.value.copy(direction = direction)
        canChangeDirection = false
    }

    private fun updatePassenger(futureDriver: Driver) =
        if (_board.value.passenger == futureDriver.head)
            pointManager.generatePassenger(futureDriver, _board.value.grid)
        else
            _board.value.passenger

    fun resetGame() {
        scoreManager.reset()
        _board.value = pointManager.generateInitialBoard()
        _running.value = true
    }

}
