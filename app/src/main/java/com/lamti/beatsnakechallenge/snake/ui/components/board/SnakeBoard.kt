package com.lamti.beatsnakechallenge.snake.ui.components.board

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.Point

@Composable
fun SnakeBoard(board: Board, colorCell: (Point) -> Color) {
    Column(
        modifier = Modifier
            .padding(10.dp)
            .border(
                border = BorderStroke(
                    width = 2.dp,
                    color = MaterialTheme.colors.background
                ),
                shape = RoundedCornerShape(2)
            )
            .padding(4.dp)
    ) {
        board.grid.forEach { row ->
            Row(modifier = Modifier.fillMaxWidth()) {
                row.forEach { point ->
                    SnakeCell(
                        modifier = Modifier.weight(1f),
                        color = colorCell(point)
                    )
                }
            }
        }
    }
}
