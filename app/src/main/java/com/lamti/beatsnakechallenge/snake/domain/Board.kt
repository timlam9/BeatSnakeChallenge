package com.lamti.beatsnakechallenge.snake.domain

import com.lamti.beatsnakechallenge.snake.domain.Board.Direction.Right

data class Board(
    val grid: List<List<Point>>,
    val direction: Direction = Right,
    val driver: Driver,
    val passenger: Point
) {

    enum class Direction {
        Right,
        Down,
        Left,
        Up
    }

}
