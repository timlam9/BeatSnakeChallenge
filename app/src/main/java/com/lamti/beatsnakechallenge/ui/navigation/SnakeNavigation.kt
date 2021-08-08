package com.lamti.beatsnakechallenge.ui.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.lamti.beatsnakechallenge.ui.SnakePreferences
import com.lamti.beatsnakechallenge.ui.activity.MainViewModel
import com.lamti.beatsnakechallenge.ui.screens.HelloScreen
import com.lamti.beatsnakechallenge.ui.screens.SnakeScreen
import com.lamti.beatsnakechallenge.ui.theme.ANIMATION_DURATION
import com.lamti.beatsnakechallenge.ui.theme.ANIMATION_OFFSET

@ExperimentalAnimationApi
@Composable
fun SnakeNavigation(viewModel: MainViewModel, preferences: SnakePreferences) {
    val navController = rememberAnimatedNavController()
    val initialRoute = remember { if (preferences.getUsername().isNullOrEmpty()) Screen.Hello.route else Screen.Snake.route }

    AnimatedNavHost(
        navController = navController,
        startDestination = initialRoute,
    ) {
        composable(
            route = Screen.Hello.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.Snake.route ->
                        slideInHorizontally(
                            initialOffsetX = { ANIMATION_OFFSET },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.Snake.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -ANIMATION_OFFSET },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
                    else -> null
                }
            },
            popEnterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.Snake.route ->
                        slideInHorizontally(
                            initialOffsetX = { -ANIMATION_OFFSET },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
                    else -> null
                }
            }
        ) {
            HelloScreen { name ->
                preferences.saveUsername(name)
                navController.navigate(Screen.Snake.route)
            }
        }
        composable(
            route = Screen.Snake.route,
            enterTransition = { initial, _ ->
                when (initial.destination.route) {
                    Screen.Hello.route ->
                        slideInHorizontally(
                            initialOffsetX = { ANIMATION_OFFSET },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) + fadeIn(animationSpec = tween(ANIMATION_DURATION))
                    else -> null
                }
            },
            exitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.Hello.route ->
                        slideOutHorizontally(
                            targetOffsetX = { -ANIMATION_OFFSET },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
                    else -> null
                }
            },
            popExitTransition = { _, target ->
                when (target.destination.route) {
                    Screen.Hello.route ->
                        slideOutHorizontally(
                            targetOffsetX = { ANIMATION_OFFSET },
                            animationSpec = tween(ANIMATION_DURATION)
                        ) + fadeOut(animationSpec = tween(ANIMATION_DURATION))
                    else -> null
                }
            }
        ) {
            SnakeScreen(
                viewModel = viewModel
            )
        }
    }
}
