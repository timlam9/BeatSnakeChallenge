package com.lamti.beatsnakechallenge.snake.domain

data class Driver(
    val head: Point,
    val body: List<Point>
) {

    fun willBoardPassenger(passenger: Point): Boolean = head == passenger

    fun hasCrashed(): Boolean = body.any { it == head }

    fun update(direction: Board.Direction, passenger: Point, gridWidth: Int, gridHeight: Int): Driver {
        val futureHead = updateHead(head, direction, gridWidth, gridHeight)
        val futureBody = updateBody(futureHead == passenger)
        return Driver(head = futureHead, body = futureBody)
    }

    private fun updateHead(
        currentDriver: Point,
        direction: Board.Direction,
        gridWidth: Int,
        gridHeight: Int
    ): Point {
        val futureDriverX = when {
            direction == Board.Direction.Right && currentDriver.x + 1 >= gridWidth -> 0
            direction == Board.Direction.Left && currentDriver.x == 0 -> gridWidth - 1
            direction == Board.Direction.Left -> currentDriver.x - 1
            direction == Board.Direction.Right -> currentDriver.x + 1
            else -> currentDriver.x
        }

        val futureDriverY = when {
            direction == Board.Direction.Down && currentDriver.y + 1 >= gridHeight -> 0
            direction == Board.Direction.Up && currentDriver.y == 0 -> gridHeight - 1
            direction == Board.Direction.Up -> currentDriver.y - 1
            direction == Board.Direction.Down -> currentDriver.y + 1
            else -> currentDriver.y
        }

        return Point(
            x = futureDriverX,
            y = futureDriverY
        )
    }

    private fun updateBody(willBoardPassenger: Boolean): List<Point> {
        val newBody = body.mapIndexed { index, _ ->
            if (index == 0) {
                head
            } else {
                body[index - 1]
            }
        }
        return if (willBoardPassenger) {
            if (body.isEmpty()) listOf(head) else newBody + body.last()
        } else { newBody
        }
    }

}
