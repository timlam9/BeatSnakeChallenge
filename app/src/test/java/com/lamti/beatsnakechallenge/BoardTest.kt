package com.lamti.beatsnakechallenge

import org.junit.Assert.*
import org.junit.Test

class BoardTest {

    private val board: Board = Board(10, 8)

    @Test
    fun `get right index from board`() {
        assertEquals(0, board.getIndex(0,0))
        assertEquals(7, board.getIndex(0,7))
        assertEquals(8, board.getIndex(1,0))
        assertEquals(15, board.getIndex(1,7))
        assertEquals(79, board.getIndex(9,7))
    }


}