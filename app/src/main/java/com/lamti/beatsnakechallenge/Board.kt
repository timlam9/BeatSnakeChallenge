package com.lamti.beatsnakechallenge

class Board(
    private val rows: Int,
    private val columns: Int
) {

    val grid: List<List<Cell>>
    val size: Int = rows * columns
    val width: Int = columns

    init {
        val rowsList: MutableList<MutableList<Cell>> = mutableListOf()
        val columnsList: MutableList<Cell> = mutableListOf()

        repeat(rows) { rowsIndex ->
            repeat(columns) { columnsIndex ->
                when {
                    rowsIndex == 5 && columnsIndex == 4 -> columnsList.add(
                        Cell(
                            Cell.Type.DriverHead,
                            getIndex(rowsIndex, columnsIndex)
                        )
                    )
                    rowsIndex == 8 && columnsIndex == 7 -> columnsList.add(
                        Cell(
                            Cell.Type.Passenger,
                            getIndex(rowsIndex, columnsIndex)
                        )
                    )
                    else -> columnsList.add(
                        Cell(
                            Cell.Type.Empty,
                            getIndex(rowsIndex, columnsIndex)
                        )
                    )
                }
            }
            rowsList.add(columnsList)
        }

        grid = rowsList
    }

    fun getIndex(rowsIndex: Int, columnsIndex: Int): Int {
        return (0 until rows * columns).chunked(columns)[rowsIndex][columnsIndex]
    }

    fun getCell(index: Int): Cell = grid.flatten()[index]

}
