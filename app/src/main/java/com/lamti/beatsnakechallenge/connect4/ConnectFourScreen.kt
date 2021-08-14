package com.lamti.beatsnakechallenge.connect4

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.main.theme.Mint100
import com.lamti.beatsnakechallenge.main.theme.Navy100
import com.lamti.beatsnakechallenge.main.theme.Orange100

@Composable
fun ConnectFourScreen(viewModel: ConnectFourViewModel = viewModel()) {
    ConnectFour(state = viewModel.state) {
        viewModel.onEvent(it)
    }
}

@Composable
fun ConnectFour(state: ConnectFourState, onEvent: (Event) -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(text = state.turn.toString())
        Board(
            board = state.board,
            onEvent = onEvent
        )
        WinningMessage(status = state.gameStatus)
        ErrorMessage(error = state.error)
    }
}

@Composable
fun WinningMessage(status: GameStatus) {
    if(status != GameStatus.Playing) {
        Text(
            text = status.getMessage(),
            color = Mint100
        )
    }
}

@Composable
fun ErrorMessage(error: Error?) {
    if(error != null) {
        Text(
            text = error.message,
            color = Orange100
        )
    }
}

@Composable
fun Board(board: Board, onEvent: (Event) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        board.columns.forEachIndexed { index, column ->
            Column(
                modifier = Modifier
                    .weight(1f)
                    .semantics {
                        contentDescription = "Column $index"
                    }
                    .clickable {
                        onEvent(Event.OnColumnClicked(index))
                    }
            ) {
                column.slots.forEach { slot ->
                    Slot(
                        modifier = Modifier
                            .semantics {
                                contentDescription = "Slot $index.${slot.index}"
                            },
                        slot = slot
                    )
                }
            }
        }
    }
}

@Composable
fun Slot(modifier: Modifier, slot: Slot) {
    val color = when (slot.availability) {
        Availability.Available -> Color.White
        Availability.Player -> Mint100
        Availability.Opponent -> Navy100
    }
    Slot(modifier = modifier, color = color)
}

@Composable
fun Slot(modifier: Modifier, color: Color) {
    Box(
        modifier = modifier
            .clip(CircleShape)
            .background(color)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_beat),
            tint = Color.White,
            contentDescription = null
        )
    }
}
