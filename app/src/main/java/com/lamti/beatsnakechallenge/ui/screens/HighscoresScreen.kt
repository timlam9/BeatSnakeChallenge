package com.lamti.beatsnakechallenge.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.data.User
import com.lamti.beatsnakechallenge.ui.theme.Orange100

@Composable
fun HighscoresScreen(users: List<User>, id: String) {
    Column(modifier = Modifier.fillMaxSize()) {
        Text(
            text = stringResource(R.string.highscores),
            style = MaterialTheme.typography.h4.copy(color = MaterialTheme.colors.onSurface, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(20.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            itemsIndexed(users) { index, user ->
                HighscoreRow(index + 1, user, id)
            }
        }
    }
}

@Composable
fun HighscoreRow(position: Int, user: User, userID: String) {
    val materialBackgroundColor = MaterialTheme.colors.background
    val materialOnBackgroundColor = MaterialTheme.colors.onBackground
    val backgroundColor = remember { if (userID == user.id) Orange100 else materialBackgroundColor }
    val textColor = remember { if (userID == user.id) Color.White else materialOnBackgroundColor }

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
