package com.lamti.beatsnakechallenge.ui.activity

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.lamti.beatsnakechallenge.data.SnakeRepository
import com.lamti.beatsnakechallenge.data.User
import com.lamti.beatsnakechallenge.domain.Board
import com.lamti.beatsnakechallenge.domain.Game
import com.lamti.beatsnakechallenge.domain.Point
import com.lamti.beatsnakechallenge.domain.SnakeControllers
import com.lamti.beatsnakechallenge.domain.SnakeSpeed
import com.lamti.beatsnakechallenge.ui.SnakePreferences
import com.lamti.beatsnakechallenge.ui.theme.Mint100
import com.lamti.beatsnakechallenge.ui.theme.MintBlend14
import com.lamti.beatsnakechallenge.ui.theme.Navy100
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModelFactory(
    val repository: SnakeRepository,
    val game: Game
) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(
                repository = repository,
                game = game
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}

class MainViewModel(
    val repository: SnakeRepository,
    val game: Game
) : ViewModel() {

    companion object {

        const val HEIGHT = 11
        const val WIDTH = 9

    }

    private fun getUser(): User = User(repository.getID(), repository.getUsername(), repository.getHighscore())

    private val _user = MutableStateFlow(getUser())
    val user: StateFlow<User> = _user

    val running: StateFlow<Boolean> = game.running
    val board: StateFlow<Board> = game.board
    val score: StateFlow<Int> = game.score

    var users: List<User> by mutableStateOf(emptyList())
        private set

    var controllers: SnakeControllers by mutableStateOf(SnakeControllers.PieController)
        private set

    var showSettings: Boolean by mutableStateOf(false)
        private set

    var snakeSpeed: SnakeSpeed by mutableStateOf(SnakeSpeed.Normal)
        private set

    init {
        getUsers()
    }

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

    fun uploadUser(user: User) {
        viewModelScope.launch {
            val id = repository.updateUser(user)
            repository.saveUsername(user.name)
            repository.saveUserID(id)
        }
    }

    fun gameOver(score: Int) {
        if (repository.getHighscore() < score) {
            repository.saveHighscore(score)
            updateHighscore()
            getUsers()
        }
    }

    private fun updateHighscore() {
        viewModelScope.launch {
            val updatedUser = User(
                id = repository.getID(),
                name = repository.getUsername(),
                highscore = repository.getHighscore()
            )
            repository.updateUser(updatedUser)
            _user.value = updatedUser
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            users = repository.getUsers()
        }
    }

}

