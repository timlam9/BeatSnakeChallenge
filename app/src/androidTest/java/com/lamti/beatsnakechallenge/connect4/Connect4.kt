package com.lamti.beatsnakechallenge.connect4

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertValueEquals
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.platform.app.InstrumentationRegistry
import com.lamti.beatsnakechallenge.connect4.data.WebSocket
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.Draw
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.OpponentWon
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.PlayerWon
import com.lamti.beatsnakechallenge.connect4.domain.Turn
import com.lamti.beatsnakechallenge.connect4.domain.Turn.Opponent
import com.lamti.beatsnakechallenge.connect4.domain.Turn.Player
import com.lamti.beatsnakechallenge.connect4.ui.ConnectFourScreen
import com.lamti.beatsnakechallenge.connect4.ui.ConnectFourViewModel
import com.lamti.beatsnakechallenge.main.ui.theme.BeatGamesTheme
import com.lamti.beatsnakechallenge.snake.ui.SnakePreferences
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class Connect4 {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun firstPlay() {
        newGame {
            announcePlayerTurn(Player)
            selectColumn(1)
            announcePlayerTurn(Opponent)
        }
    }

    @Test
    fun cannotSelectFilledColumn() {
        newGame {
            repeat(6) {
                selectColumn(1)
            }
            selectColumn(1)

            showMessage("Cannot select an already filled board")
        }
    }

    @Test
    fun playerConnectsFourHorizontally() {
        newGame {
            selectColumn(1)
            selectColumn(1)
            selectColumn(2)
            selectColumn(2)
            selectColumn(3)
            selectColumn(3)
            selectColumn(4)

            showMessage(PlayerWon.getMessage())
        }
    }

    @Test
    fun opponentConnectsFourHorizontally() {
        newGame {
            selectColumn(1)
            selectColumn(1)
            selectColumn(2)
            selectColumn(2)
            selectColumn(3)
            selectColumn(3)
            selectColumn(5)
            selectColumn(4)
            selectColumn(5)
            selectColumn(4)

            showMessage(OpponentWon.getMessage())
        }
    }

    @Test
    fun playerConnectsFourVertically() {
        newGame {
            selectColumn(1)
            selectColumn(2)
            selectColumn(1)
            selectColumn(2)
            selectColumn(1)
            selectColumn(2)
            selectColumn(1)

            showMessage(PlayerWon.getMessage())
        }
    }

    @Test
    fun opponentConnectsFourVertically() {
        newGame {
            selectColumn(1)
            selectColumn(2)
            selectColumn(3)
            selectColumn(2)
            selectColumn(4)
            selectColumn(2)
            selectColumn(5)
            selectColumn(2)

            showMessage(OpponentWon.getMessage())
        }
    }

    @Test
    fun playerConnectsFourAscendingDiagonally() {
        newGame {
            selectColumn(1)
            selectColumn(2)
            selectColumn(2)
            selectColumn(3)
            selectColumn(4)
            selectColumn(3)
            selectColumn(3)
            selectColumn(4)
            selectColumn(5)
            selectColumn(4)
            selectColumn(4)

            showMessage(PlayerWon.getMessage())
        }
    }

    @Test
    fun opponentConnectsFourAscendingDiagonally() {
        newGame {
            selectColumn(1)
            selectColumn(2)
            selectColumn(3)
            selectColumn(2)
            selectColumn(3)
            selectColumn(3)
            selectColumn(4)
            selectColumn(4)
            selectColumn(4)
            selectColumn(4)
            selectColumn(5)
            selectColumn(5)
            selectColumn(5)
            selectColumn(6)
            selectColumn(5)
            selectColumn(5)

            showMessage(OpponentWon.getMessage())
        }
    }

    @Test
    fun playerConnectsFourDescendingDiagonally() {
        newGame {
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(5)
            selectColumn(5)
            selectColumn(5)
            selectColumn(5)
            selectColumn(4)
            selectColumn(4)
            selectColumn(3)
            selectColumn(4)
            selectColumn(6)
            selectColumn(3)

            showMessage(PlayerWon.getMessage())
        }
    }

    @Test
    fun opponentConnectsFourDescendingDiagonally() {
        newGame {
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(5)
            selectColumn(5)
            selectColumn(4)
            selectColumn(5)
            selectColumn(1)
            selectColumn(4)
            selectColumn(1)
            selectColumn(3)

            showMessage(OpponentWon.getMessage())
        }
    }

    @Test
    fun draw() {
        newGame {
            selectColumn(0)
            selectColumn(1)
            selectColumn(0)
            selectColumn(1)
            selectColumn(0)
            selectColumn(1)
            selectColumn(1)
            selectColumn(0)
            selectColumn(1)
            selectColumn(0)
            selectColumn(1)
            selectColumn(0)
            selectColumn(2)
            selectColumn(3)
            selectColumn(2)
            selectColumn(3)
            selectColumn(2)
            selectColumn(3)
            selectColumn(3)
            selectColumn(2)
            selectColumn(3)
            selectColumn(2)
            selectColumn(3)
            selectColumn(2)
            selectColumn(4)
            selectColumn(5)
            selectColumn(4)
            selectColumn(5)
            selectColumn(4)
            selectColumn(5)
            selectColumn(5)
            selectColumn(4)
            selectColumn(5)
            selectColumn(4)
            selectColumn(5)
            selectColumn(4)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)
            selectColumn(6)

            showMessage(Draw.getMessage())
        }
    }

    @Test
    fun showRestartGameWhenGameIsOver() {
        newGame {
            selectColumn(1)
            selectColumn(1)
            selectColumn(2)
            selectColumn(2)
            selectColumn(3)
            selectColumn(3)
            selectColumn(4)

            playAgainButtonIsDisplayed()
        }
    }

    private val snakePreferences = SnakePreferences(
        InstrumentationRegistry.getInstrumentation().context.getSharedPreferences(
            SnakePreferences.SNAKE_PREFERENCES,
            Context.MODE_PRIVATE
        )
    )
    private val okHttpClient = OkHttpClient()
    private val webSocket = WebSocket(okHttpClient)

    private fun newGame(function: ConnectFourRobot.() -> Unit) =
        ConnectFourRobot(composeTestRule, ConnectFourViewModel(webSocket, snakePreferences)).apply { function() }

}

class ConnectFourRobot(
    private val composeTestRule: ComposeContentTestRule,
    private val viewModel: ConnectFourViewModel
) {

    init {
        composeTestRule.setContent {
            BeatGamesTheme {
                ConnectFourScreen(viewModel = viewModel)
            }
        }
    }

    fun announcePlayerTurn(turn: Turn) {
        composeTestRule.onNodeWithText("${turn.name}'s turn!").assertIsDisplayed()
    }

    fun selectColumn(column: Int) {
        composeTestRule.onNodeWithContentDescription("Column $column").performClick()
    }

    fun showMessage(message: String) {
        composeTestRule.onNodeWithText(message).assertIsDisplayed()
    }

    fun playAgainButtonIsDisplayed() {
        composeTestRule.onNodeWithText("Play again").assertIsDisplayed()
    }

    fun clickRestartGame() {
        composeTestRule.onNodeWithText("Play again").performClick()
    }

    fun boardIsEmpty() {
        composeTestRule.onNodeWithContentDescription("Column 1").assertValueEquals("ValueTest")
    }

}


