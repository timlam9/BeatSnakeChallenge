package com.lamti.beatsnakechallenge.snake.ui.components.score

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.main.ui.theme.Orange100
import com.lamti.beatsnakechallenge.snake.data.User

@Composable
fun HighscoreRow(position: Int, user: User, userEmail: String) {
    val materialBackgroundColor = MaterialTheme.colors.background
    val materialOnBackgroundColor = MaterialTheme.colors.onBackground
    val backgroundColor = remember { if (userEmail == user.email) Orange100 else materialBackgroundColor }
    val textColor = remember { if (userEmail == user.email) Color.White else materialOnBackgroundColor }

    Card(
        shape = RoundedCornerShape(8.dp),
        backgroundColor = backgroundColor,
    ) {
        Row(
            modifier = Modifier
                .height(60.dp)
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = position.toString(),
                style = MaterialTheme.typography.body1.copy(color = textColor, fontWeight = FontWeight.Bold),
                modifier = Modifier.weight(0.5f)
            )
            Text(
                text = user.name,
                style = MaterialTheme.typography.body1.copy(color = textColor),
                modifier = Modifier.weight(6f)
            )
            Text(
                text = user.highscore.toString(),
                style = MaterialTheme.typography.body1.copy(color = textColor)
            )
        }
    }
}
