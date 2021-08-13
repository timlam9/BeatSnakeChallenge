package com.lamti.beatsnakechallenge.main.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lamti.beatsnakechallenge.snake.data.User
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.Point
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.main.screens.HelloScreen
import com.lamti.beatsnakechallenge.snake.ui.screens.HighscoresScreen
import com.lamti.beatsnakechallenge.snake.ui.screens.SnakeScreen
import com.lamti.beatsnakechallenge.snake.ui.screens.SnakeState

@ExperimentalAnimationApi
@Composable
fun SnakeNavigation(
    user: User,
    users: List<User>,
    uploadUser: (User) -> Unit,
    onSettingsClicked: () -> Unit,
    onHighscoresClicked: () -> Unit,
    currentSnakeSpeed: SnakeSpeed,
    currentController: SnakeControllers,
    onSpeedChanged: (SnakeSpeed) -> Unit,
    onControllerChanged: (SnakeControllers) -> Unit,
    board: Board,
    score: Int,
    showSettings: Boolean,
    onChangeDirection: (Board.Direction) -> Unit,
    restartGame: () -> Unit,
    gameOver: (Int) -> Unit,
    colorCell: (Point) -> Color
) {
    val navController = rememberNavController()
    val initialRoute = if (user.name.isEmpty()) Screen.Hello.route else Screen.Snake.route

    NavHost(
        navController = navController,
        startDestination = initialRoute,
    ) {
        composable(
            route = Screen.Hello.route
        ) {
            HelloScreen { name ->
                uploadUser(User(name = name))
                navController.navigate(Screen.Snake.route)
            }
        }
        composable(
            route = Screen.Snake.route
        ) {
            SnakeScreen(
                snakeState = SnakeState(
                    score = score,
                    board = board,
                    controllers = currentController,
                    showSettings = showSettings,
                    snakeSpeed = currentSnakeSpeed
                ),
                onSettingsClicked = { onSettingsClicked() },
                onHighscoresClicked = {
                    onHighscoresClicked()
                    navController.navigate(Screen.Highscores.route)
                },
                highscore = user.highscore,
                onSpeedChanged = { onSpeedChanged(it) },
                onControllerChanged = { onControllerChanged(it) },
                onChangeDirection = { onChangeDirection(it) },
                restartGame = restartGame,
                gameOver = { gameOver(it) },
                colorCell = { colorCell(it) }
            )
        }
        composable(
            route = Screen.Highscores.route
        ) {
            HighscoresScreen(
                users = users,
                id = user.id ?: ""
            )
        }
    }
}
