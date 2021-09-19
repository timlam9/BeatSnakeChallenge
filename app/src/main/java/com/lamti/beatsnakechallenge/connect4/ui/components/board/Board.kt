package com.lamti.beatsnakechallenge.connect4.ui.components.board

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.connect4.domain.Board

@Composable
fun Board(board: Board, isGamePlaying: Boolean, onColumnClicked: (Int) -> Unit) {
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
                        if (isGamePlaying) onColumnClicked(index)
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
