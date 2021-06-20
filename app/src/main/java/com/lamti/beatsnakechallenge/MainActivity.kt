package com.lamti.beatsnakechallenge

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.lamti.beatsnakechallenge.MainViewModel.Companion.SPEED
import com.lamti.beatsnakechallenge.ui.theme.BeatSnakeChallengeTheme
import com.lamti.beatsnakechallenge.ui.theme.Orange100
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels()
    private val joystickColor = Orange100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        updateGameLooper()
        setContent {
            BeatSnakeChallengeTheme {
                Surface(color = MaterialTheme.colors.background) {
                    MainContent(viewModel = viewModel)
                }
            }
        }
    }

    private fun updateGameLooper() {
        lifecycleScope.launchWhenResumed {
            while (isActive) {
                delay(SPEED)
                if (viewModel.running.value) {
                    Log.d("GAME_STATE", "Running: ${viewModel.running.value}")
                    viewModel.updateGame()
                }
            }
        }
    }

    @Composable
    private fun MainContent(viewModel: MainViewModel) {
        val board by viewModel.board.collectAsState()
        val score by viewModel.score.collectAsState()

        Column(modifier = Modifier.fillMaxSize()) {
            Score(score = score.toString())
            SnakeGrid(board = board) { point ->
                viewModel.colorCell(point)
            }
            Joystick(
                onUpClick = { viewModel.changeDirection(Board.Direction.Up) },
                onLeftClick = { viewModel.changeDirection(Board.Direction.Left) },
                onRightClick = { viewModel.changeDirection(Board.Direction.Right) },
                onDownClick = { viewModel.changeDirection(Board.Direction.Down) },
                onCenterClick = { viewModel.restartGame() }
            )
        }
    }

    @Composable
    private fun Score(score: String) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Text(
                text = "Beat Snake",
                style = MaterialTheme.typography.h4.copy(fontWeight = FontWeight.ExtraBold),
                color = Orange100
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                text = "score: $score",
                style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.SemiBold),
                color = MaterialTheme.colors.onBackground
            )
        }
    }

    @Composable
    fun SnakeGrid(board: Board, colorCell: (Point) -> Color) {
        Column(
            modifier = Modifier.padding(start = 20.dp, end = 20.dp)
        ) {
            board.grid.forEach { row ->
                Row(modifier = Modifier.fillMaxWidth()) {
                    row.forEach { point ->
                        Cell(
                            modifier = Modifier.weight(1f),
                            color = colorCell(point)
                        )
                    }
                }
            }
        }
    }

    @Composable
    fun Joystick(
        onUpClick: () -> Unit,
        onLeftClick: () -> Unit,
        onRightClick: () -> Unit,
        onDownClick: () -> Unit,
        onCenterClick: () -> Unit
    ) {
        BoxWithConstraints(
            modifier = Modifier.fillMaxSize()
        ) {
            val smallSide = maxWidth / 8
            val largeSide = maxWidth / 6

            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    UpButton(
                        modifier = Modifier
                            .width(smallSide)
                            .height(largeSide)
                            .clickable { onUpClick() }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LeftButton(
                        modifier = Modifier
                            .width(largeSide)
                            .height(smallSide)
                            .clickable { onLeftClick() }
                    )
                    Box(
                        modifier = Modifier
                            .size(smallSide)
                            .padding(6.dp)
                            .clip(CircleShape)
                            .background(joystickColor)
                            .clickable { onCenterClick() }
                    )
                    RightButton(
                        modifier = Modifier
                            .width(largeSide)
                            .height(smallSide)
                            .clickable { onRightClick() }
                    )
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DownButton(
                        modifier = Modifier
                            .width(smallSide)
                            .height(largeSide)
                            .clickable { onDownClick() }
                    )
                }
            }
        }
    }

    @Composable
    private fun UpButton(modifier: Modifier) {
        Canvas(
            modifier = modifier
        ) {
            val path = Path()
            val padding = 20f
            val yPoint = size.height / 3 + size.height / 4
            val centerPointX = size.width / 2
            val centerPointY = size.height - padding

            path.moveTo(padding, padding)
            path.lineTo(size.width - padding, padding)
            path.lineTo(size.width - padding, yPoint)
            path.lineTo(centerPointX, centerPointY)
            path.lineTo(padding, yPoint)

            drawPath(
                path = path,
                brush = SolidColor(joystickColor)
            )
        }
    }

    @Composable
    fun LeftButton(modifier: Modifier) {
        Canvas(
            modifier = modifier
        ) {
            val path = Path()
            val padding = 20f
            val xPoint = size.width / 3 + size.width / 4
            val yPoint = size.height - padding
            val centerPointX = size.width - padding
            val centerPointY = size.height / 2

            path.moveTo(padding, padding)
            path.lineTo(padding, yPoint)
            path.lineTo(xPoint, yPoint)
            path.lineTo(centerPointX, centerPointY)
            path.lineTo(xPoint, padding)

            drawPath(
                path = path,
                brush = SolidColor(joystickColor)
            )
        }
    }

    @Composable
    fun RightButton(modifier: Modifier) {
        Canvas(
            modifier = modifier
        ) {
            val path = Path()
            val padding = 20f
            val xPoint = size.width - (size.width / 3 + size.width / 4)
            val yPoint = size.height - padding
            val centerPointX = size.width - padding
            val centerPointY = size.height / 2

            path.moveTo(padding, centerPointY)
            path.lineTo(xPoint, padding)
            path.lineTo(centerPointX, padding)
            path.lineTo(centerPointX, yPoint)
            path.lineTo(xPoint, yPoint)

            drawPath(
                path = path,
                brush = SolidColor(joystickColor)
            )
        }
    }

    @Composable
    fun DownButton(modifier: Modifier) {
        Canvas(
            modifier = modifier
        ) {
            val path = Path()
            val padding = 20f
            val yPoint = size.height - (size.height / 3 + size.height / 4)
            val centerPointX = size.width / 2

            path.moveTo(padding, yPoint)
            path.lineTo(centerPointX, padding)
            path.lineTo(size.width - padding, yPoint)
            path.lineTo(size.width - padding, size.height - padding)
            path.lineTo(padding, size.height - padding)

            drawPath(
                path = path,
                brush = SolidColor(joystickColor)
            )
        }
    }

    @Composable
    fun Cell(modifier: Modifier, color: Color = MaterialTheme.colors.onBackground) {
        val dayNightColor = if (color == Color.White) MaterialTheme.colors.onBackground else color

        Box(
            modifier = modifier
                .clip(CircleShape)
                .background(dayNightColor)
        ) {
            if (dayNightColor == MaterialTheme.colors.onBackground) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_beat),
                    tint = dayNightColor,
                    contentDescription = null
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.ic_beat),
                    contentDescription = null
                )
            }
        }
    }

}
