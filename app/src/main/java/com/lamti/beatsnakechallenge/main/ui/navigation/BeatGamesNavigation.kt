package com.lamti.beatsnakechallenge.main.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lamti.beatsnakechallenge.connect4.ui.ConnectFourScreen
import com.lamti.beatsnakechallenge.main.ui.activity.MainViewModel
import com.lamti.beatsnakechallenge.main.ui.screens.HelloScreen
import com.lamti.beatsnakechallenge.main.ui.screens.MenuScreen
import com.lamti.beatsnakechallenge.snake.data.User
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.ui.SnakeState
import com.lamti.beatsnakechallenge.snake.ui.screens.HighscoresScreen
import com.lamti.beatsnakechallenge.snake.ui.screens.SnakeScreen

@Composable
fun BeatGamesNavigation(
    viewModel: MainViewModel,
    user: User,
    board: Board,
    score: Int
) {
    val navController = rememberNavController()
    val initialRoute = if (user.email.isEmpty()) Screen.Hello.route else Screen.Menu.route

    NavHost(
        navController = navController,
        startDestination = initialRoute,
    ) {
        composable(
            route = Screen.Hello.route
        ) {
            HelloScreen { name, email, password ->
                viewModel.register(name, email, password)
                navController.navigate(Screen.Menu.route) {
                    popUpTo(Screen.Hello.route) {
                        inclusive = true
                    }
                }
            }
        }
        composable(
            route = Screen.Menu.route
        ) {
            MenuScreen(
                onConnectFourClicked = { navController.navigate(Screen.Connect4.route) },
                onSnakeClicked = { navController.navigate(Screen.Snake.route) }
            )
        }
        composable(
            route = Screen.Snake.route
        ) {
            SnakeScreen(
                snakeState = SnakeState(
                    score = score,
                    board = board,
                    controllers = viewModel.controllers,
                    showSettings = viewModel.showSettings,
                    snakeSpeed = viewModel.snakeSpeed
                ),
                onSettingsClicked = { viewModel.onSettingsClicked() },
                onHighscoresClicked = {
                    viewModel.closeSettingsDialog()
                    navController.navigate(Screen.Highscores.route)
                },
                highscore = user.highscore,
                onSpeedChanged = { viewModel.changeSnakeSpeed(it) },
                onControllerChanged = { viewModel.setController(it) },
                onChangeDirection = { viewModel.changeDirection(it) },
                restartGame = { viewModel.restartGame() },
                gameOver = { viewModel.gameOver(it) },
                colorCell = { viewModel.colorCell(it) }
            )
        }
        composable(
            route = Screen.Highscores.route
        ) {
            HighscoresScreen(viewModel = viewModel, email = user.email)
        }
        composable(
            route = Screen.Connect4.route
        ) {
            ConnectFourScreen()
        }
    }
}
