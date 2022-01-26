package com.lamti.beatsnakechallenge.connect4.ui.components.board

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.connect4.domain.Board

@Composable
fun Board(board: Board, isGamePlaying: Boolean, onColumnClicked: (Int) -> Unit) {
    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val slotSize = screenWidth / 8
    val padding = 10.dp

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(padding),
        horizontalArrangement = Arrangement.Center
    ) {
        board.columns.forEachIndexed { index, column ->
            Column(
                modifier = Modifier
                    .semantics {
                        contentDescription = "Column $index"
                    }
                    .clickable {
                        if (isGamePlaying) onColumnClicked(index)
                    }
            ) {
                column.slots.forEach { slot ->
                    Slot(
                        modifier = Modifier.size(slotSize - 1.dp)
                            .semantics {
                                contentDescription = "Slot $index.${slot.index}"
                            },
                        slot = slot,
                        size = slotSize
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun BoardPreview() {
    Board(
        board = Board.generateEmptyBoard(),
        isGamePlaying = true
    ) {

    }
}
