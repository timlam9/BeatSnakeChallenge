package com.lamti.beatsnakechallenge.main.ui.activity

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lamti.beatsnakechallenge.main.domain.RegistrationCredentials
import com.lamti.beatsnakechallenge.main.ui.theme.Mint100
import com.lamti.beatsnakechallenge.main.ui.theme.MintBlend14
import com.lamti.beatsnakechallenge.main.ui.theme.Navy100
import com.lamti.beatsnakechallenge.snake.data.SnakeRepository
import com.lamti.beatsnakechallenge.snake.data.User
import com.lamti.beatsnakechallenge.snake.domain.Board
import com.lamti.beatsnakechallenge.snake.domain.Game
import com.lamti.beatsnakechallenge.snake.domain.Point
import com.lamti.beatsnakechallenge.snake.domain.SnakeControllers
import com.lamti.beatsnakechallenge.snake.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.snake.ui.UsersViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: SnakeRepository,
    private val game: Game
) : ViewModel() {

    private fun getUser(): User = repository.getUser()

    val running: StateFlow<Boolean> = game.running
    val board: StateFlow<Board> = game.board
    val score: StateFlow<Int> = game.score

    private val _user = MutableStateFlow(getUser())
    val user: StateFlow<User> = _user

    var usersState: MutableState<UsersViewState> = mutableStateOf(UsersViewState.Loading)
        private set

    var controllers: SnakeControllers by mutableStateOf(SnakeControllers.PieController)
        private set

    var showSettings: Boolean by mutableStateOf(false)
        private set

    var snakeSpeed: SnakeSpeed by mutableStateOf(SnakeSpeed.Normal)
        private set

    fun closeSettingsDialog() {
        showSettings = false
    }

    fun colorCell(point: Point): Color = when (point) {
        in board.value.driver.body -> MintBlend14
        board.value.driver.head -> Navy100
        board.value.passenger -> Mint100
        else -> Color.White
    }

    fun changeDirection(direction: Board.Direction) {
        game.changeDirection(direction)
    }

    fun updateGame() {
        game.update()
    }

    fun restartGame() {
        if (!running.value) {
            game.resetGame()
        }
    }

    fun onSettingsClicked() {
        showSettings = !showSettings
    }

    fun setController(controller: SnakeControllers) {
        controllers = controller
    }

    fun changeSnakeSpeed(speed: SnakeSpeed) {
        snakeSpeed = speed
    }

    fun register(name: String, email: String, password: String) {
        viewModelScope.launch {
            repository.register(
                RegistrationCredentials(
                    name = name,
                    email = email,
                    password = password
                )
            )
        }
    }

    fun gameOver(score: Int) {
        if (repository.getHighscore() < score) {
            repository.saveHighscore(score)
            updateHighscore()
        }
    }

    private fun updateHighscore() {
        viewModelScope.launch {
            _user.value = repository.updateUser()
        }
    }

    fun getUsers() {
        viewModelScope.launch {
            usersState.value = repository.getUsers()
        }
    }

}
