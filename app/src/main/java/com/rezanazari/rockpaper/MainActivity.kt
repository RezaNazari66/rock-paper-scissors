package com.rezanazari.rockpaper

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.rezanazari.rockpaper.components.Board
import com.rezanazari.rockpaper.model.ScoreBoard
import com.rezanazari.rockpaper.model.GameState
import com.rezanazari.rockpaper.ui.theme.RockPaperScissorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel: MainViewModel by viewModels()
        setContent {
            RockPaperScissorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen(
                        viewModel.state.value,
                        setScreenWidth = viewModel::setScreenWidthAndInitializeItems,
                        toggleGameRunning = viewModel::toggleGameRunningState,
                        restartGame = viewModel::restartGame
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    state: GameState,
    setScreenWidth: (Dp) -> Unit,
    toggleGameRunning: () -> Unit,
    restartGame: () -> Unit,
) {

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Board(state, setScreenWidth)
        ScoreBoard(state.scoreBoard)
        Spacer(modifier = Modifier.weight(1f))
        ControllerButtons(toggleGameRunning, state.isGameStarted, restartGame)
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun ControllerButtons(
    startPauseGame: () -> Unit,
    isGameStarted: Boolean,
    restartGame: () -> Unit,
) {
    Button(onClick = { startPauseGame() }) {
        Text(
            text = stringResource(if (isGameStarted) R.string.pause else R.string.start)
        )
    }
    Button(onClick = { restartGame() }) {
        Text(text = stringResource(R.string.restart))
    }
}

@Composable
private fun ScoreBoard(
    scoreBoard: ScoreBoard?,
) {
    val isChallengeValid = scoreBoard?.first?.type != null && scoreBoard.second?.type != null
    if (isChallengeValid) {
        Text(
            text = stringResource(
                id = R.string.vs,
                scoreBoard?.first?.type.toString(),
                scoreBoard?.second?.type.toString()
            )
        )
        scoreBoard?.winner?.type?.let {
            Text(text = stringResource(id = R.string.winner, it))
        }
    }
}


