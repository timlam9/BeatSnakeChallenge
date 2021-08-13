package com.lamti.beatsnakechallenge.main.navigation

sealed class Screen(open val route: String = "") {

    object Hello : Screen("hello")

    object Snake : Screen("snake")

    object Highscores : Screen("highscores")

}
