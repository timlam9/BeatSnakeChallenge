package com.lamti.beatsnakechallenge.main.ui.navigation

sealed class Screen(open val route: String = "") {

    object Hello : Screen("hello")

    object Menu : Screen("menu")

    object Snake : Screen("snake")

    object Highscores : Screen("highscores")

    object Connect4 : Screen("connect4")

}
