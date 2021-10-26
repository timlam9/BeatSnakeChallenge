package com.lamti.beatsnakechallenge.connect4.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lamti.beatsnakechallenge.connect4.domain.ConnectFourState
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus
import com.lamti.beatsnakechallenge.connect4.domain.GameStatus.Playing
import com.lamti.beatsnakechallenge.connect4.ui.components.ErrorMessage
import com.lamti.beatsnakechallenge.connect4.ui.components.InfoText
import com.lamti.beatsnakechallenge.connect4.ui.components.PlayAgainButton
import com.lamti.beatsnakechallenge.connect4.ui.components.WinningMessage
import com.lamti.beatsnakechallenge.connect4.ui.components.board.Board
import com.lamti.beatsnakechallenge.snake.ui.components.LoadingIndicator

@Composable
fun ConnectFourScreen(viewModel: ConnectFourViewModel = hiltViewModel()) {
    if (viewModel.state.gameStatus == GameStatus.SearchingOpponent) {
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.height(100.dp))
            InfoText(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = viewModel.state.gameStatus.getMessage()
            )
            LoadingIndicator()
        }
    } else {
        ConnectFourScreen(
            state = viewModel.state,
            onColumnClicked = { viewModel.onEvent(Event.OnColumnClicked(it)) },
            onRestartClicked = { viewModel.onEvent(Event.OnRestartClicked) }
        )
    }
}

@Composable
fun ConnectFourScreen(
    state: ConnectFourState,
    onColumnClicked: (Int) -> Unit,
    onRestartClicked: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        val centerModifier = Modifier.align(Alignment.CenterHorizontally)

        Board(
            board = state.board,
            isGamePlaying = state.gameStatus == Playing,
            onColumnClicked = onColumnClicked
        )
        Spacer(modifier = Modifier.height(64.dp))
        InfoText(
            modifier = centerModifier,
            text = state.turn.toString()
        )
        Spacer(modifier = Modifier.height(32.dp))
        WinningMessage(
            modifier = centerModifier,
            status = state.gameStatus
        )
        ErrorMessage(
            modifier = centerModifier,
            error = state.error
        )
        Spacer(modifier = Modifier.height(16.dp))
        PlayAgainButton(
            modifier = centerModifier,
            isGameOver = state.gameStatus != Playing,
            onRestartClicked = onRestartClicked
        )
    }
}
