package com.lamti.beatsnakechallenge

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeContentTestRule
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import com.lamti.beatsnakechallenge.connect4.ConnectFourScreen
import com.lamti.beatsnakechallenge.connect4.ConnectFourViewModel
import com.lamti.beatsnakechallenge.connect4.Turn
import com.lamti.beatsnakechallenge.connect4.Turn.Opponent
import com.lamti.beatsnakechallenge.connect4.Turn.Player
import com.lamti.beatsnakechallenge.main.theme.BeatGamesTheme
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

            showMessage("Player won!")
        }
    }


    private fun newGame(function: ConnectFourRobot.() -> Unit) =
        ConnectFourRobot(composeTestRule, ConnectFourViewModel()).apply { function() }

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

}


