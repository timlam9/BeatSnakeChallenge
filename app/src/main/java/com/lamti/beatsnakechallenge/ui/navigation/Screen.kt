package com.lamti.beatsnakechallenge.ui.navigation

sealed class Screen(open val route: String = "") {

    object Hello : Screen("hello")

    object Snake : Screen("snake")

}
