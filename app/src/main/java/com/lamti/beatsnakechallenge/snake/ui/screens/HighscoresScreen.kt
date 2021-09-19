package com.lamti.beatsnakechallenge.snake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.snake.data.User
import com.lamti.beatsnakechallenge.snake.ui.components.score.HighscoreRow

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
