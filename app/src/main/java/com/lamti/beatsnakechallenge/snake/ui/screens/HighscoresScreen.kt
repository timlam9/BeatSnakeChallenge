package com.lamti.beatsnakechallenge.snake.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.lamti.beatsnakechallenge.R
import com.lamti.beatsnakechallenge.main.activity.MainViewModel
import com.lamti.beatsnakechallenge.snake.ui.UsersViewState.Error
import com.lamti.beatsnakechallenge.snake.ui.UsersViewState.Loading
import com.lamti.beatsnakechallenge.snake.ui.UsersViewState.Success
import com.lamti.beatsnakechallenge.snake.data.User
import com.lamti.beatsnakechallenge.snake.ui.components.score.HighscoreRow

@Composable
fun HighscoresScreen(viewModel: MainViewModel, id: String) {
    val usersState by remember { viewModel.usersState }
    LaunchedEffect(key1 = true) { viewModel.getUsers() }

    when(usersState) {
        is Error -> Text((usersState as Error).message)
        Loading -> LoadingIndicator()
        is Success -> HighscoresScreen((usersState as Success).users, id)
    }
}

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
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        CircularProgressIndicator(
            modifier = Modifier.align(
                alignment = Alignment.Center
            )
        )
    }
}
