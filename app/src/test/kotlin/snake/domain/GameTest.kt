package snake.domain

import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.Driver
import com.lamti.beatsnakechallenge.snake.domain.Game
import com.lamti.beatsnakechallenge.snake.domain.Point
import com.lamti.beatsnakechallenge.snake.domain.PointManager
import org.junit.Assert.assertEquals
import org.junit.Test

class GameTest {

    companion object {

        private const val HEIGHT = 10
        private const val WIDTH = 5

    }

    private val testedClass: Game = Game(HEIGHT, WIDTH)

    @Test
    fun `update game`() {
        val driver = Driver(Point(3,5), emptyList())

        testedClass.update()

        assertEquals(driver, testedClass.board.value.driver)
    }

    @Test
    fun `change direction`() {
        val givenDirection = Board.Direction.Down

        testedClass.changeDirection(givenDirection)

        assertEquals(givenDirection, testedClass.board.value.direction)
    }

    @Test
    fun `reset game`() {
        val pointManager = PointManager(HEIGHT, WIDTH)

        testedClass.resetGame()

        assertEquals(pointManager.generateInitialBoard().grid, testedClass.board.value.grid)
    }

}
