package com.lamti.beatsnakechallenge.domain

class PointManager(private val height: Int, private val width: Int) {

    fun generatePassenger(driver: Driver, grid: List<List<Point>>): Point = grid
        .flatten()
        .shuffled()
        .first { point ->
            (driver.body + driver.head).none { it == point }
        }

    fun generateInitialBoard() = Board(
        grid = generateGrid(),
        driver = generateDriver(),
        passenger = generateInitialPassenger(),
    )

    private fun generateGrid(): List<List<Point>> = List(height) { y ->
        List(width) { x ->
            Point(x, y)
        }
    }

    private fun generateDriver(): Driver = Driver(
        head = Point(width / 2, height / 2),
        body = emptyList()
    )

    private fun generateInitialPassenger(driverHead: Point = Point(width / 2, height / 2)): Point =
        Point(
            x = (0 until width).filterNot { it == driverHead.x }.random(),
            y = (0 until height).filterNot { it == driverHead.y }.random()
        )

}